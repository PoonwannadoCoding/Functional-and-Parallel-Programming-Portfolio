object UseLib extends App {
  def onlyBeginsWithLower(xs: Vector[String]): Vector[String] = {

    xs.filterNot(_.isEmpty).filter(_.charAt(0).isLower)

  }

  println(onlyBeginsWithLower(Vector("Space", "old", "cool", "Foo", "")))

  def longestString(xs: Vector[String]): Option[String] = {
    if xs.isEmpty then None
    else Some(xs.maxBy(_.length))
  }

  println(longestString(Vector()))

  def longestLowercase(xs: Vector[String]): Option[String] = {
    if xs.isEmpty then None
    else Some(xs.filterNot(_.isEmpty).filter(_.charAt(0).isLower).maxBy(_.length))
  }

  println(longestLowercase(Vector("wASIKDJIOWU", "AoJKIKJIO", "iaiw9kasdo")))
}
