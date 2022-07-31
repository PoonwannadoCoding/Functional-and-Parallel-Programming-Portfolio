
object AllPerm extends App {
  def allPerm(n: Int): List[List[Int]] = n match {
    case 0 => List(List())
    case 1 => List(List(1))
    case _ => permutation(n, allPerm(n-1))
  }

  def permutation(n: Int, bigList: List[List[Int]]): List[List[Int]] = bigList.size match{
    case 0 => Nil
    case _ =>
      insertAll(n,bigList.head,0) ::: permutation(n, bigList.tail)
  }

  def insertAll(n: Int,list: List[Int], pos: Int): List[List[Int]] = {
    if pos > list.size then Nil
    else insertAt(list,pos,n) :: insertAll(n,list,pos+1)
  }

  def insertAt(list: List[Int], i: Int, value: Int): List[Int] = list match {
    case head :: tail if i > 0 => head :: insertAt(tail, i-1, value)
    case _ => value :: list
  }


  println(allPerm(4))

}

//ref: https://www.geeksforgeeks.org/scala-flatmap-method/ https://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/