package solver

object Newton {

  // your implementation of the Newton method that takes a function f: Double => Double
  // and its derivative df: Double => Double  (take note of the types),
  // and computes a root of f using the Newton's method with the given 
  // guess: Double starting value

  def solve(f: Double => Double, df: Double => Double, 
            guess: Double = 1.0): Option[Double] = {
    try{
     val result: Option[Double] = Some(guess - f(guess)/df(guess))
      result
    }
    catch
    case _ => None
     // replace me with real code
  }

}
