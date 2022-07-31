object TurnIt extends App {
  def transpose(A: List[List[Int]]): List[List[Int]] = A.head match {
    case Nil => Nil
    case _ => getHead(A) :: transpose(dropHead(A))
  }

  def dropHead(A: List[List[Int]]): List[List[Int]] = A match {
    case Nil => Nil
    case _ => A.head.tail :: dropHead(A.tail)
  }

  def getHead(A: List[List[Int]]): List[Int] = A match {
    case Nil => Nil
    case _ => A.head.head :: getHead(A.tail)
  }
/*
  println(transpose(List(List(1,2,3,4), List(5,6,7,8), List(9,10,11,12))))
  println(getHead(List(List(1,2,3,4), List(5,6,7,8), List(9,10,11,12))))
  println(dropHead(List(List(1,2,3,4), List(5,6,7,8), List(9,10,11,12))))
*/


}

