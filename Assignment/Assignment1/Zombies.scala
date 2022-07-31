import scala.annotation.tailrec

object Zombies extends App {
  def countBad(hs: List[Int]): Int = {
    mergeSort(hs,0)._2
  }

  def mergeSort(list: List[Int], count: Int): (List[Int], Int) = list match {
    case Nil => (list,count)
    case h::Nil => (list,count)
    case _ =>
      val (left, right) = splitHalf(list,List(), list.size/2)
      counter(mergeSort(left,count)._1, mergeSort(right,count)._1, mergeSort(left, count)._2 + mergeSort(right, count)._2)

  }

  @tailrec
  def splitHalf(list: List[Int], left: List[Int], index: Int): (List[Int], List[Int]) = {
    if left.size == index then (left, list)
    else
      splitHalf(list.tail,left :+ list.head, index)
  }
  
  def counter(left: List[Int], right: List[Int], count: Int): (List[Int], Int) = (left, right) match {
    case (_, Nil) => (left,count)
    case (Nil, _) => (right,count)
    case (leftHead :: leftTail, rightHead :: rightTail) =>
      if leftHead >= rightHead then (leftHead :: counter(leftTail, right, count)._1, counter(leftTail, right, count)._2)
      else (rightHead :: counter(left, rightTail, count+left.length)._1, counter(left, rightTail, count+left.length)._2)
  }

  println(countBad(List(35, 22, 10)))
  println(countBad(List(3,1,4,2)))
  println(countBad(List(5,4,11,7)))
  println(countBad(List(1, 7, 22, 13, 25, 4, 10, 34, 16, 28, 19, 31)))

}

// ref: https://www.youtube.com/watch?v=K4MQXjmxU8M, https://medium.com/analytics-vidhya/playing-with-scala-merge-sort-d382fb1a32ff, https://www.baeldung.com/scala/tuples
