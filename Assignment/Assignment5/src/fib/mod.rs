use num_bigint::{BigUint, ToBigInt, ToBigUint};
use rayon::iter::{IntoParallelIterator, ParallelIterator};

#[allow(dead_code)]
pub fn par_fib_seq(n: u32) -> Vec<num_bigint::BigUint> {
    let mut first = (1,1,1,0);

    if n == 0{
        return vec![];
    } else if n == 1{
        return vec![1.to_biguint().unwrap()]
    } else {
        let mut base = Vec::new();

        (1..n).into_iter().for_each(|x|base.push(first));

        let (mut metrix, start) = prefix_scan(base);
        metrix.push(first);
        let mut meep: Vec<num_bigint::BigUint> = metrix.into_iter().map(|x| x.0.to_biguint().unwrap()).collect();
        meep.sort();
        return meep;
    }

}


pub fn multiply(F:(i32,i32,i32,i32), M: (i32, i32, i32, i32)) -> (i32, i32, i32, i32){

    let topLeft = (F.0 * M.0 + F.1 * M.2);
    let topRight = (F.0 * M.1 + F.1 * M.3);
    let bottomLeft = (F.2 * M.0 + F.3 * M.2);
    let bottomRight = (F.2 * M.1 + F.3 * M.3);

    let mut result:(i32,i32,i32,i32) =(topLeft,topRight,bottomLeft,bottomRight);
        return result
}



fn prefix_scan(xs: Vec<(i32,i32,i32,i32)>) -> (Vec<(i32,i32,i32,i32)>, (i32,i32,i32,i32)) {
    use rayon::iter::*;
    if xs.is_empty() { return (vec![], (1,1,1,0)); }
    let half = xs.len()/2;
    let (c_prefix, mut c_sum) = prefix_scan(
        (0..half).into_par_iter()
            .map(|i|multiply(xs[2*i],xs[2*i+1]))
            .collect::<Vec<(i32,i32,i32,i32)>>()
    );

    let mut c_multiply: Vec<(i32,i32,i32,i32)> = (0..half).into_par_iter().flat_map(|i| vec![c_prefix[i], multiply(c_prefix[i], xs[2*i])]).collect();

    if xs.len() % 2 == 1 {
        c_multiply.push(c_sum);
        c_sum = multiply(c_sum, xs[xs.len()-1]);

    }

    return  (c_multiply, c_sum);
}

/*
Work = O(n) <= power + O(1) <= multiply
Span = O(log^2n)
 */




#[cfg(test)]
mod tests {
    use num_bigint::ToBigUint;
    use crate::fib::{par_fib_seq};

    #[test]
    fn my_test() {
        assert_eq!(vec![1.to_biguint().unwrap()
                        ,1.to_biguint().unwrap()
                        ,2.to_biguint().unwrap()
                        ,3.to_biguint().unwrap()
                        ,5.to_biguint().unwrap()
                        ,8.to_biguint().unwrap()
                        ,13.to_biguint().unwrap()], par_fib_seq(7));
    }

}


