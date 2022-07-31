package solver

object Process {
  // gives a "pretty-print" string form of the expression
  def stringify(e: Expression): String = e match {
    case Constant(c) => c.toString
    case Var(name) => name
    case Sum(l, r) => stringify(l) + " + " + stringify(r)
    case Prod(l @ Sum(_, _), r @ Sum(_, _)) => "(" + stringify(l) + ") * (" + stringify(r) + ")"
    case Prod(l @ Sum(_, _), r) => "(" + stringify(l) + ") * " + stringify(r)
    case Prod(l, r @ Sum(_, _)) => stringify(l) + " * (" + stringify(r) + ")"
    case Prod(l, r) => stringify(l) + " * " + stringify(r)
    case Power(b, e) => stringify(b) + "^" + stringify(e)
  }

  // evaluates a given expression e: Expression using
  // the variable settings in varAssn: Map[String, Double],
  // returning the evaluation result as a Double.

  // Example: eval(e, Map("x" -> 4.0)) evaluates the expression 
  // with the variable "x" set to 4.0.
  def eval(e: Expression, varAssn: Map[String, Double]): Double = e match {
    case Constant(name) => name
    case Var(n) => varAssn(n)
    case Sum(e1,e2) => eval(e1, varAssn) + eval(e2, varAssn)
    case Prod(e1, e2) => eval(e1, varAssn) * eval(e2, varAssn)
    case Power(b, e) => math.pow(eval(b, varAssn), eval(e,varAssn))
  }

  // symbolically differentiates an expression e: Expression with 
  // respect to the variable varName: String
  def differentiate(e: Expression, varName: String): Expression = e match {
    case Constant(n) => Constant(0)
    case Sum(e1, e2) => Sum(differentiate(e1, varName), differentiate(e2, varName))
    case Prod(e1,e2) => Sum(Prod(e1,differentiate(e2, varName)),Prod(e2,differentiate(e1, varName)))
    case Power(b, e) =>
      val ex: Expression = Constant(math.log(eval(b,Map(varName -> 1))))
      if differentiate(b,varName).equals(Constant(0)) then Prod(ex, Power(b,e))
      else Prod(Prod(e, Power(b, Sum(e, Constant(-1)))), differentiate(b, varName))
    case Var(name) => if name.equals(Var(name)) then Constant(1) else e

  }

  // forms a new expression that simplifies the given expression e: Expression
  // the goal of this function is produce an expression that is easier to
  // evaluate and/or differentiate.  If there's a canonical form you'd like to
  // follow, use this function to put an expression in this form.
  // you may leave this function blank if can't find any use. 
  def simplify(e: Expression): Expression = {

    ???
  }

}
