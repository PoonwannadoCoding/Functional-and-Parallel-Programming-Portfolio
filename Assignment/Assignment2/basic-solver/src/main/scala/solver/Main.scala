package solver

import scala.annotation.tailrec

object Solver extends App {
  // solves expString == 0 for the variable in varName with an initial guess
  // specified. We'll assume that the given expression has a root.

  println(solve("x^2 - 4.0", "x", 1.0))

  def solve(expString: String, varName: String, guess: Double): Double = {
    val ex = Parser(expString)
    val exp: Expression = ex.get
    val f = (key: Double) => Process.eval(exp, Map(varName -> key))
    val df = Process.differentiate(exp,varName)
    val getDf = (key: Double) => Process.eval(df, Map(varName -> key))

    @tailrec
    def accurate(f: Double => Double, df: Double => Double, guess: Double): Double = {
      val newGuess: Double = Newton.solve(f, df, guess).get
      if math.abs(newGuess - guess) < 0.000000000000000000000000000001 then guess
      else {
        accurate(f,df, newGuess)
      }
    }

    accurate(f, getDf, guess)

    // TODO: complete the implementation. This will construct the 
    // appropriate functions and call Newton.solve
    // <- remove me when you're done
  }

}
