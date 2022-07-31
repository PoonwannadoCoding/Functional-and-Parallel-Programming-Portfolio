object Aggregate extends App {
  // TODO: implement these functions for real!
  def myMin(p: Double, q: Double, r: Double): Double = {
    math.min(math.min(p,q),r)
  }
  def myMean(p: Double, q: Double, r: Double): Double = {
    (p+q+r)/3
  }

  def myMed(p: Double, q: Double, r: Double): Double = {
    math.max(math.min(p,q),math.min(q,r))

  }

  //print(myMed(5,8,60))
}

