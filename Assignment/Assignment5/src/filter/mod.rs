mod cwslice;

use bitvec::macros::internal::funty::Fundamental;
use rayon::iter::*;
use cwslice::UnsafeSlice;

#[allow(dead_code)]
pub fn par_filter<F>(xs: &[i32], p: F) -> Vec<i32>
where 
    F: Fn(i32) -> bool + Send + Sync
{
    if xs.is_empty(){
        return Vec::new();
    }

    let mut v_bool:Vec<i32> = xs.into_par_iter().map(|i|{
     if(p(i.clone())){
         1
     } else{
         0
     }
    }).clone().collect::<Vec<i32>>();


    let (mut index, c_sum) = plus_scan(v_bool.clone());

    if index.len()%2 != 0{
        index.push(c_sum);
    }

    unsafe {
        let mut result:Vec<i32> = Vec::with_capacity(c_sum.as_usize());
        result.set_len(c_sum.as_usize());
        let mkitunsafe = UnsafeSlice::new(&mut result);
        v_bool.into_par_iter().enumerate().for_each(|i|{
            if i.1 == 1{
                mkitunsafe.write(index[i.0] as usize, xs[i.0])
            }
        });

        return result;
    }


}



fn isOdd(input: i32) -> bool{
    if input%2 != 0{
        return true
    } else{
        return false
    }
}

fn plus_scan(xs: Vec<i32>) -> (Vec<i32>, i32) {
    use rayon::iter::*;
    if xs.is_empty() { return (vec![], 0); }
    let half = xs.len()/2;
    let (c_prefix, mut c_sum) = plus_scan(
        (0..half).into_par_iter()
            .map(|i| xs[2*i] + xs[2*i+1])
            .collect::<Vec<i32>>()
    );
    let mut pfs: Vec<i32> = (0..half).into_par_iter()
        .flat_map(|i| vec![c_prefix[i], c_prefix[i]+xs[2*i]])
        .collect();
    if xs.len() % 2 == 1 {
        pfs.push(c_sum);
        c_sum += xs[xs.len()-1];
    }

    (pfs, c_sum)
}


#[cfg(test)]
mod tests {
    use rayon::iter::*;
    use crate::filter::{isOdd, par_filter, plus_scan};

    #[test]
    fn my_test() {
        assert_eq!(vec![1,3,5],par_filter(&[1,2,3,4,5], isOdd));
    }
}
