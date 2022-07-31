import scala.annotation.tailrec

object Spaghetti extends App {
  def spaghetti: LazyList[String] = loop("1")

  def loop(i: String): LazyList[String] ={
    i #:: loop(readAloud(i))
  }

  def readAloud(xs: String): String = {
    if xs.isEmpty then ""
    else helper(xs.tail, xs.head, 1)
  }

  def helper(xs: String, oldNum: Char, freq: Int): String = xs match {
    case "" => freq+oldNum.toString
    case _ =>
      if xs.head == oldNum then helper(xs.tail, xs.head, freq + 1)
      else freq + oldNum.toString + helper(xs.tail, xs.head, 1)
  }

  println(spaghetti.take(5).toList)


  println(ham.take(6).toList)

  def ham: LazyList[String] = whileLoop(1).flatMap(gray)

  def whileLoop(i: Int): LazyList[Int] = {
    i #:: whileLoop(i+1)
  }

  def gray(n: Int): List[String] = {

    if (n == 1) {
      List("0", "1")
    } else {
      val original = gray(n - 1)
      val reversed = original.reverse
      original.map("0" + _) ++ reversed.map("1" + _)
    }
  }
}
