
#[allow(dead_code)]
pub fn is_palindrome(s: &str) -> bool {
    let mut result = true;
    let mut index = 0;
    let size = s.len();
    if size > 1{
        while index < size/2 {
            let mut front = s.chars().nth(index).unwrap();
            let mut back = s.chars().nth(s.len()-index-1).unwrap();
            if  front != back{
                return false;
            }
            index = index + 1;
        }
    }
    return result;

}

#[allow(dead_code)]
pub fn is_pangram(s: &str) -> bool {
    let mut lower = s.to_lowercase();

    for mut c in 'a'..='z'{
        if !lower.contains(c){
            return false;
        }
    }
    return true;

}

#[cfg(test)]
mod tests {
    use crate::pangrindrome::{is_palindrome, is_pangram};

    #[test]
    fn basic_is_palindrome() {
        assert_eq!(true, is_palindrome("r"));
        assert_eq!(true, is_palindrome("abba"));
        assert_eq!(true, is_palindrome("abcba"));
        assert_eq!(false, is_palindrome("abc"));
    }

    #[test]
    fn basic_pangram() {
        let quick_brown_fox = "The quick brown fox jumps over the lazy Dog";
        assert_eq!(true, is_pangram(quick_brown_fox));
        let quick_prairie_dog = "The quick prairie dog jumps over the lazy fox";
        assert_eq!(false, is_pangram(quick_prairie_dog));
    }

    #[test]
    fn my_test() {
        assert_eq!(true, is_palindrome("kayak"));
        assert_eq!(true, is_palindrome("deified"));
        assert_eq!(true, is_palindrome("repaper"));
        assert_eq!(false, is_palindrome("compile"));
        let quick_brown_fox = "Glib jocks quiz nymph to vex dwarf.";
        assert_eq!(true, is_pangram(quick_brown_fox));
        let quick_brown_fox = "The three boxing wizards jump quickly.";
        assert_eq!(false, is_pangram(quick_brown_fox));
    }
}
