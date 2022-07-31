use std::fs::File;
use std::{fs, io};
use std::collections::HashMap;
use std::io::{BufRead, BufReader, Read};
use std::num::FpCategory::Nan;
use std::ops::{Add, Deref};
use std::ptr::slice_from_raw_parts;
use std::str::{from_utf8, FromStr};
use bitvec::ptr::BitPtrError::Null;
use chashmap::CHashMap;
use rayon::*;
use rayon::iter::{IntoParallelIterator, IntoParallelRefIterator, ParallelIterator};
use rayon::prelude::{ParallelSliceMut, ParallelString};


#[derive(Debug, PartialEq)]
struct FlightRecord {
    unique_carrier: String,
    actual_elapsed_time: i32,
    arrival_delay: i32,
}

pub fn par_split<'a>(st_buf: &'a str, split_char: char) -> Vec<&'a str> {
    let mut result = Vec::new();

    let mut v_bool = st_buf.par_chars().into_par_iter().map(|chr|{
        if isItThatChar(chr, split_char){
            1
        } else{
            0
        }
    }).collect::<Vec<i32>>();

    let (mut index, mut size) = plus_scan(v_bool);
    let mut j = 0;
    for i in 1..index.len()-1{
        if index[i] < index[i+1]{
            let word = &st_buf[j..i];

            result.push(word.clone());
            j = i+1;
        }
    }
    result.push(&st_buf[j..index.len()]);
    return result
}


fn isItThatChar(st_buf: char, split_char: char) -> bool{
    if st_buf == split_char{
        return true
    } else {
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



#[allow(dead_code)]
fn parse_line(line: &str) -> Option<FlightRecord> {
    let split_line = par_split(line,',');

        let flight = FlightRecord{
            unique_carrier: String::from(split_line[8]),
            actual_elapsed_time:if split_line[11].parse::<i32>().is_ok() {split_line[11].parse().unwrap()} else {0},
            arrival_delay: if split_line[14].parse::<i32>().is_ok() {split_line[14].parse().unwrap()} else {0},
        };
        Some(flight)
}

#[allow(dead_code)]
pub fn ontime_rank(filename: &str) -> Result<Vec<(String, f64)>, io::Error> {
    println!("INITIATE");
    let file_contents = fs::read_to_string(filename).unwrap();
    let slice:&str = &file_contents;
    let lines:Vec<&str> = slice.lines().collect();
    println!("COMPLETE LINE");


    let onTime = CHashMap::new();
    lines.into_par_iter().for_each(|i| {
        let check = parse_line(i);

        if check.is_some() {
            let fr = check.unwrap();
            if fr.arrival_delay <= 0 {
                onTime.upsert(fr.unique_carrier, || (1,1), |(s, t)| {*s+=1; *t+=1;})
            } else {
                onTime.upsert(fr.unique_carrier, || (0,1), |(_, t)| *t+=1)
            }
        }
    });


    onTime.remove(&String::from("UniqueCarrier"));


    let mut percentage: Vec<(String, f64)> = onTime.into_iter().map(|(k,(o,c))| (k.clone(), o as f64/ c as f64)).collect();

    percentage.par_sort_unstable_by(|(_,p), (_,p1)| p1.partial_cmp(p).unwrap());


    Ok(percentage)

}





#[cfg(test)]
mod tests {
    use std::str::FromStr;
    use rayon::iter::*;
    use crate::ontime::{FlightRecord, ontime_rank, par_split, parse_line};

    #[test]
    fn my_test() {
        assert_eq!(vec!["a", "hhh", "ab", "hello", "world", "", "meh"],par_split("a,hhh,ab,hello,world,,meh",',' ));
    }

    #[test]
    fn pars_line_test() {
        let flight = FlightRecord{
            unique_carrier: String::from("WN") ,
            actual_elapsed_time:i32::from_str("0").unwrap(),
            arrival_delay:i32::from_str("0").unwrap(),
        };
        assert_eq!(Some(flight),parse_line("2008,1,3,4,1620,1620,1639,1655,WN,810,N648SW,NA,95,70,NA,0,IND,MCI,451,3,6,0,,0,NA,NA,NA,NA,NA"));
    }

}