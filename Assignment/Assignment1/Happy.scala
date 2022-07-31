import scala.annotation.tailrec

object Happy extends App {
  // TODO: write these functions!
  def sumOfDigitsSquared(n: Int): Int = {
    val sum = 0
    sumHelper(n,sum)
  }

  @tailrec
  def sumHelper(n:Int, sum: Int): Int = {
    if (n < 10){
      sum+n*n
    } else {
      sumHelper(n/10, sum+(n%10) * (n%10))
    }
  }
  
  @tailrec
  def isHappy(n: Int): Boolean = {
    if (n == 1){
      true
    } else if(n == 4){
      false
    }
    else {
      isHappy(sumOfDigitsSquared(n))
    }
  }

  def kThHappy(k: Int): Int = {
    val num = 1
    val numCorrect = 0
    kThHelper(k, num, numCorrect)
  }

  @tailrec
  def kThHelper(k:Int, num: Int, numCorrect: Int): Int = {
    if(k == numCorrect){
      num-1
    } else {
      if(isHappy(num)){
        kThHelper(k,num+1,numCorrect+1)
      } else {
        kThHelper(k,num+1,numCorrect)
      }
    }
  }

  //print(kThHappy(19))
}
