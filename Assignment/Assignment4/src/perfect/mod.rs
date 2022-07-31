use std::{cmp::Ordering, ops::Range};
use crate::perfect::Classification::Deficient;

#[allow(dead_code)]
#[derive(Debug, Eq, PartialEq)] // make this enum type support equality test (i.e., ==)
pub enum Classification {
    Perfect,
    Deficient,
    Excessive,
}

#[allow(dead_code)]
pub fn classify_perfect(n: u64) -> Classification {
    let mut num = 0;
    let mut i = 1;
    while i < n {
        if n%i == 0{
            num += i
        }
        i = i +1
    }
    return if num == n {
        Classification::Perfect
    } else if num < n {
        Classification::Deficient
    } else {
        Classification::Excessive
    }
    //panic!("Not yet implemented!")
}

#[allow(dead_code)]
pub fn select_perfect(range: Range<u64>, kind: Classification) -> Vec<u64> {
    let mut result = Vec::new();
    for mut i in range{

        if classify_perfect(i) == kind {
            result.push(i);
        }
    }
    return result;
    //panic!("Not yet implemented!")
}


#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn basic_classify() {
        use Classification::*;
        assert_eq!(classify_perfect(1), Deficient);
        assert_eq!(classify_perfect(6), Perfect);
        assert_eq!(classify_perfect(12), Excessive);
        assert_eq!(classify_perfect(28), Perfect);
    }

    #[test]
    fn basic_select() {
        use Classification::*;
        assert_eq!(select_perfect(1..10_000, Perfect), vec![6, 28, 496, 8128]);
        assert_eq!(
            select_perfect(1..50, Excessive),
            vec![12, 18, 20, 24, 30, 36, 40, 42, 48]
        );
        assert_eq!(
            select_perfect(1..11, Deficient),
            vec![1, 2, 3, 4, 5, 7, 8, 9, 10]
        );
    }

    #[test]
    fn my_test() {
        use Classification::*;
        assert_eq!(classify_perfect(16), Deficient);
        assert_eq!(classify_perfect(496), Perfect);
        assert_eq!(classify_perfect(54), Excessive);
        assert_eq!(
            select_perfect(1..100, Excessive),
            vec![12, 18, 20, 24, 30, 36, 40, 42, 48, 54, 56, 60, 66, 70, 72, 78, 80, 84, 88, 90, 96]
        );

        assert_eq!(
            select_perfect(1..100, Excessive),
            vec![12, 18, 20, 24, 30, 36, 40, 42, 48, 54, 56, 60, 66, 70, 72, 78, 80, 84, 88, 90, 96 ]
        );

        assert_eq!(select_perfect(1..1000, Perfect), vec![6, 28, 496]);
    }



}
