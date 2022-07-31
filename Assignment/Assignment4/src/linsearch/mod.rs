#[allow(dead_code)]
pub fn par_lin_search<T: Eq + Sync>(xs: &[T], k: &T) -> Option<usize> {
    //panic!("Not yet implemented!")
    if xs.len() <= 1{
        return Some(0);
    } else {
        let (left, right) = xs.split_at((xs.len())/2);
        let (leftResult, rightResult) = rayon::join(|| helper(left,k,0), || helper(right, k,left.len()));
        if leftResult != None{
            return leftResult;
        }
        else{
            return rightResult ;
        }
    }
}

fn helper<T: Eq + Sync>(xs: &[T], k: &T, leftSize: usize) -> Option<usize>{
    for (i, j) in xs.iter().enumerate(){
        if *j == *k {
            return Some(i+leftSize);
        }
    } None
}
/*
(ii) As a comment block above your implementation, analyze the work and span of your implementation.
If you write a recurrence, point out what it solves to. Explain your reasoning

    W = O(n/2) + O(n/2) + O(1)
      = O(n)

 , S = 2W(n/2) + O(1)
     = O(logn)


 */


#[cfg(test)]
mod tests {
    use crate::linsearch::par_lin_search;

    #[test]
    fn basic_lin_search() {
        let xs = vec![3, 1, 4, 2, 7, 3, 1, 9];
        assert_eq!(par_lin_search(&xs, &3), Some(0));
        assert_eq!(par_lin_search(&xs, &5), None);
        assert_eq!(par_lin_search(&xs, &1), Some(1));
        assert_eq!(par_lin_search(&xs, &2), Some(3));
    }

    #[test]
    fn my_test() {
        let lst = vec![5, 10, 54, 21, 23, 45, 30];
        assert_eq!(par_lin_search(&lst, &4), None);
        assert_eq!(par_lin_search(&lst, &5), Some(0));
        assert_eq!(par_lin_search(&lst, &10), Some(1));
        assert_eq!(par_lin_search(&lst, &54), Some(2));
        assert_eq!(par_lin_search(&lst, &21), Some(3));
        assert_eq!(par_lin_search(&lst, &23), Some(4));
        assert_eq!(par_lin_search(&lst, &45), Some(5));
        assert_eq!(par_lin_search(&lst, &30), Some(6));
    }
}
