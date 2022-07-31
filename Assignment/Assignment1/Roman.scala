import scala.annotation.tailrec
object Roman extends App {
  // TODO: implement this
  def toRoman(n: Int): String = {
    val result = ""
    val digit: List[Int] = List[Int](1000,900,500,400,100,90,50,40,10,9,5,4,1)
    val roman: List[String] = List[String]("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    if n < 10 then conToRoman(n, result, digit, roman)
    else conToRoman(n, result, digit, roman)
  }
  @tailrec
  def conToRoman(n: Int, result: String, digit: List[Int], roman: List[String]): String = {
    if n < 1 then result //base case
    else if n >= digit.head then conToRoman(n-digit.head, result + roman.head,digit, roman)
    else  conToRoman(n, result,digit.tail, roman.tail)
  }

  //println(toRoman(54))
}
