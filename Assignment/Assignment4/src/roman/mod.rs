use std::collections::HashMap;

#[allow(dead_code)]
pub fn to_roman(n: u16) -> String {
    let roam_char = vec!["M", "CM", "DC", "D", "CD", "C", "XC", "LX", "L", "XL", "X", "IX", "VI", "V", "IV", "I"];
    let digit = vec![1000, 900, 600,500,400, 100,90,60, 50,40, 10,9,6,5,4, 1];
    let mut number = n;
    let mut i = 0;
    let mut result = String::new();
    while number > 0 {
        if number >= digit[i] {
            number = number - digit[i];
            result.push_str(roam_char[i]);
        } else {
            i = i + 1;
        }
    }
    return result;
}
    
    //panic!("Not yet implemented!")


#[allow(dead_code)]
pub fn parse_roman(roman_number: &str) -> u16 {

    let roman = HashMap::from([
        ("M", 1000),
        ("D", 500),
        ("C", 100),
        ("L", 50),
        ("X", 10),
        ("V", 5),
        ("I",1)
    ]);

    let mut i = 0;
    let mut result = 0;
    while i < roman_number.len(){
        if i != roman_number.len()-1 && roman.get(&*roman_number.chars().nth(i).unwrap().to_string()) < roman.get(&*roman_number.chars().nth(i+1).unwrap().to_string()){
            result += roman.get(&*roman_number.chars().nth(i+1).unwrap().to_string()).unwrap() - roman.get(&*roman_number.chars().nth(i).unwrap().to_string()).unwrap();
            i += 2
        } else{
            result += roman.get(&*roman_number.chars().nth(i).unwrap().to_string()).unwrap();
            i += 1;
        }
    }

    return result;
}

#[cfg(test)]
mod tests {
    use super::{parse_roman, to_roman};

    #[test]
    fn basic_digits() {
        assert_eq!("I", to_roman(1));
        assert_eq!("V", to_roman(5));
        assert_eq!("X", to_roman(10));
        assert_eq!("L", to_roman(50));
        assert_eq!("C", to_roman(100));
    }

    #[test]
    fn basic_mixture() {
        assert_eq!("II", to_roman(2));
        assert_eq!("IV", to_roman(4));
        assert_eq!("IX", to_roman(9));
        assert_eq!("XII", to_roman(12));
        assert_eq!("XIV", to_roman(14));
        assert_eq!("MCMLIV", to_roman(1954));
    }

    #[test]
    fn basic_parsing() {
        assert_eq!(3, parse_roman("III"));
        assert_eq!(4, parse_roman("IV"));
        assert_eq!(8, parse_roman("VIII"));
        assert_eq!(19, parse_roman("XIX"));
    }

    #[test]
    fn my_test() {
        assert_eq!(12, parse_roman("XII"));
        assert_eq!(450, parse_roman("CDL"));
        assert_eq!(3475, parse_roman("MMMCDLXXV"));
        assert_eq!(69, parse_roman("LXIX"));
        assert_eq!(420, parse_roman("CDXX"));


    }
}
