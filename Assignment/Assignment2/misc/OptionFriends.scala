import scala.annotation.tailrec

object OptionFriends extends App {

  @tailrec
  def lookup(xs: List[(String, String)], key: String): Option[String] = xs match {
    case Nil => None
    case _ =>
      if xs.head._1.equals(key) then Some (xs.head._2)
      else lookup(xs.tail, key)
  }


  println(lookup(List(("a", "xy"), ("c", "pq"), ("b", "je")), "b"))


  def resolve(userIdFromLoginName: String => Option[String],
              majorFromUserId: String => Option[String],
              divisionFromMajor: String => Option[String],
              averageScoreFromDivision: String => Option[Double],
              loginName: String): Double = {
    userIdFromLoginName(loginName).flatMap(majorFromUserId).flatMap(divisionFromMajor).flatMap(averageScoreFromDivision).getOrElse(0.0)

  }

  val userIdFromLoginName = (x:String) => lookup(List(("boss_is_not_cool","1"), ("poon_is_cooler","2")), x)
  val majorFromUserId = (x:String) => lookup(List(("1", "CS"), ("2", "IR")), x)
  val divisionFromMajor = (x: String) => lookup(List(("CS", "Science"), ("IR", "Monke")),x)
  val averageScoreFromDivision = (x:String) => if (x == "Monke") Option(100.0) else Option(99.0)
  println(resolve(userIdFromLoginName,majorFromUserId,divisionFromMajor,averageScoreFromDivision, "poon_is_cooler"))

}
