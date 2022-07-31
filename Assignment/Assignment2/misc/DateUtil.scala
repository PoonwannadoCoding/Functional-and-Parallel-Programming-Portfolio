import scala.annotation.tailrec

object DateUtil extends App {
  type Date = (Int, Int, Int)

  def isOlder(x: Date, y: Date): Boolean = {
    if y._3 > x._3 then true
    else if y._2 > y._2 && y._3 == x._3 then true
    else if y._1 > x._1 && y._2 == x._2 && y._3 == x._3 then true
    else false

  }

  println(isOlder((21,5,2022), (20,5,2002)))

  def numberInMonth(xs: List[Date], month: Int): Int = {

    xs.count(_._2 == month)
  }

  println(numberInMonth(List((1,2,2011), (2,12,2011), (4,2,2011)),2))

  def numberInMonths(xs: List[Date], months: List[Int]): Int = {
    xs.count(x => months.contains(x._2))
  }

  println(numberInMonths( List((1,2,2011), (2,12,2011), (4,2,2011), (4,10,2020)) , List(2, 12)))

  def datesInMonth(xs: List[Date], month: Int): List[Date] = {
    xs.filter(_._2 == month)
  }

  println(datesInMonth( List((1,2,2011), (2,12,2011), (4,2,2011), (2,3,2011), (10,2,2011)), 2))

  def datesInMonths(xs: List[Date], months: List[Int]): List[Date] = {
    xs.filter( x => months.contains(x._2))
  }
  println(datesInMonths( List((1,2,2011), (2,12,2011), (4,2,2011), (2,3,2011), (10,2,2011), (4,8,2011), (2,7,2011), (10,9,2011)), List(2,7)))

  def dateToString(d: Date): String = {
    val month: List[(String, Int)] = List(("January",1), ("February",2), ("March",3), ("April",4), ("May",5), ("June",6), ("July",7), ("August",8), ("September",9), ("October",10), ("November",11), ("December",12))

    month.filter(_._2.equals(d._2)).head._1+"-"+d._1+"-"+d._3
  }

  println( dateToString(12,5,2014))

  def whatMonth(n: Int, yr: Int): Int = {

    val normalYear: List[Int] = List(0,31,59,90,120,151,181,212,243,273,304,334,365)
    val leapYear: List[Int] = List(0,31,60,91,121,152,182,213,244,274,305,335,366)

    if (yr%4==0 || yr%400 == 0) && yr%100 != 0 then leapYear.count(x => n > x)
    else normalYear.count(x => n > x)

  }

  println(whatMonth(61,2013))

  def oldest(dates: List[Date]): Option[Date] = {
    if dates.isEmpty then None
    else
      val lessYear = dates.filter(_._3 == dates.map(_._3).min)
      val lessMonth = lessYear.filter(_._2 == lessYear.map(_._2).min)
      val lessDate = lessMonth.filter(_._1 == lessMonth.map(_._1).min)

      Some(lessDate.head)

  }

  println(oldest( List((1,6,2011), (5,10,2015), (12,4,2002))))


  def isReasonableDate(d: Date) = {
    val normalYear: List[(Int, Int)] = List((31,1),(28,2),(31,3),(30,4),(31,5),(30,6),(31,7),(31,8),(30,9),(31,10),(30,11),(31,12))
    val leapYear: List[(Int, Int)] = List((31,1),(29,2),(31,3),(30,4),(31,5),(30,6),(31,7),(31,8),(30,9),(31,10),(30,11),(31,12))

    if d._3 > 0 && d._2 > 0 && d._2 <= 12 && d._1 > 0 then {
      if (d._3%4==0 || d._3%400 == 0) && d._3%100 != 0 then leapYear.exists(x => x._1 >= d._1 && x._2 == d._2)
      else normalYear.exists(x => x._1 >= d._1 && x._2 == d._2)
    }
    else false
  }

  println(isReasonableDate( (29,2,2017) ))

}
