import scala.annotation.tailrec

object Aloud extends App {
  // TODO: Implement me
  def readAloud(xs: List[Int]): List[Int] = {
    if xs.isEmpty then Nil
    else helper(xs.tail, xs.head, 1)
  }

  def helper(xs: List[Int], oldNum: Int, freq: Int): List[Int] = xs match {
    case Nil => List[Int](freq, oldNum)
    case h::t => 
      if xs.head == oldNum then helper(xs.tail, xs.head, freq + 1)
      else freq :: oldNum :: helper(xs.tail, xs.head, 1)
    
  }

  //print(readAloud(List(3,3,-10,-10,-10)))
  //println(readAloud(List()))
  
}
