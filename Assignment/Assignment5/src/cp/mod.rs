use std::cmp::min;
use rayon::iter::{IntoParallelIterator, IntoParallelRefIterator, ParallelIterator};
use rayon::prelude::ParallelSliceMut;

#[allow(dead_code)]
pub fn par_closest_distance(points: &[(i32, i32)]) -> i64  {
    let mut sorted: Vec<(i32,i32)> = points.par_iter().map(|&t| t).collect();

    sorted.par_sort_unstable_by_key(|&p|p.0);

    let (minDistance, _) = cp_helper(&sorted);
    let sqrtDistance = (minDistance as f64).sqrt();
    //println!("{}", sqrtDistance.clone() as f64);
    sqrtDistance as i64
}
#[allow(dead_code)]
fn cp_helper(points: &[(i32, i32)]) -> (i64, Vec<(i32,i32)>) {
    if points.len() <= 3 {
        let n = points.len();
        let mut min_val = i64::max_value();

        (0..n).into_iter().for_each(|i| {
            (i + 1..n).into_iter().for_each(|j| min_val = min(min_val, distance(points[i], points[j])))
        });

        let mut sorted = points.to_vec();
        sorted.sort_by_key(|p| p.1);

        return (min_val, sorted)
    }

    let half = points.len() / 2;
    let (left, right) = points.split_at(half);
    let ((l, mut L), (r, mut R)) = rayon::join(|| cp_helper(left),
                                               || cp_helper(right));

    let mut sorted = Vec::with_capacity(points.len());
    unsafe { sorted.set_len(points.len()); }

    sorted = [L, R].concat();

    let d = min(l, r);
    let band: Vec<(i32, i32)> = sorted.par_iter().filter(|(x, y)| ((points[half].0 - x).abs() as i64) < d)
        .map(|t| *t).collect();
    let dd = (0..band.len()).into_par_iter().map(|i| {
        let bound = if i + 7 > band.len() { band.len() } else { i + 7 };
        (i + 1..bound).into_par_iter().map(|j| distance(band[j], band[i]))
            .min().unwrap_or(i64::max_value())
    }).reduce(|| d, |m, x| i64::min(m, x));

    return (i64::min(d,dd), sorted);
}


fn distance(pointA: (i32, i32), pointB: (i32, i32)) -> i64{

    let dis = ((pointA.0 as i64 - pointB.0 as i64).pow(2) + (pointA.0 as i64 - pointB.0 as i64).pow(2));
    return dis
}


#[cfg(test)]
mod tests {

    use rayon::iter::*;
    use crate::cp::{par_closest_distance};

    #[test]
    fn my_test() {
        assert_eq!(2,par_closest_distance(&[(0,0),(2,0), (10,5), (8,6), (6,9)]));
    }

}
