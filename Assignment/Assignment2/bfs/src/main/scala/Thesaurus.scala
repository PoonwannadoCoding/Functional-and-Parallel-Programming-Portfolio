import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.Source

object Thesaurus extends App{

  val defaultEncoding = "ISO8859-1"

  def load(filename: String) = {

    val yeet = Source.fromFile(filename)
    try {
      val line = yeet.getLines()
      val line2 = line.next() //ISO8859-1


      @tailrec
      def getToDic(currentLine: Iterator[String], store: Map[String, Set[String]]): Map[String, Set[String]] = {
        if currentLine.nonEmpty then {
          val key = currentLine.next()

          val length = key.substring(key.lastIndexOf("|")).tail.toInt // Get num of lines
          val cleanKey = key.substring(0, key.lastIndexOf("|"))
          val value = findValue(currentLine, length, Set())
          getToDic(currentLine, store + (cleanKey -> value))
        }
        else
          store
      }

      @tailrec
      def findValue(line: Iterator[String], limit: Int, value: Set[String]): Set[String] = {
        if line.nonEmpty then {
          if limit > 0 then {
            val nextValue = line.next()
            val nextFilter = nextValue.substring(nextValue.trim.lastIndexOf(")|")).tail.tail.split('|')
            val fin = nextFilter.toSet

            findValue(line: Iterator[String], limit - 1, value ++ fin)
          }
          else value
        }
        else
          value
      }

      val result = getToDic(line, Map.empty[String, Set[String]])
      result
    }
    finally {
    yeet.close()
    }


  }



  def linkage(thesaurusFile: String): String => String => Option[List[String]] = {
    val dataBase: Map[String,Set[String]] = load(thesaurusFile)
    val nbrs = (key: String) => dataBase.getOrElse(key, Nil).toSet
    val list = (key: String) => (find: String) =>
      if mapToList(GraphBFS.bfs(nbrs, key)._1, List.empty[String], key,find).isEmpty then None
      else Option(mapToList(GraphBFS.bfs(nbrs, key)._1, List.empty[String], key,find):+ find)
    list
  }




  @tailrec
  def mapToList( map: Map[String,String], result: List[String], start: String, des: String): List[String] = {
    if map.tail.isEmpty then Nil
    else {
      if des == start then result.reverse
      else {
        val newWord: String = map.getOrElse(des,"")
        mapToList(map.tail, result :+ newWord, start, newWord)
      }
    }
  }

  val dataBase: Map[String,Set[String]] = load("/home/worawit/Documents/GitHub/a2-starter/thesaurus_db.txt")
  val nbrs = (key: String) => dataBase.getOrElse(key, Nil).toSet

  val findLinks = linkage("/home/worawit/Documents/GitHub/a2-starter/thesaurus_db.txt")
  // the above should parse the given database and store it in the closure of findLinks
  val f = findLinks("clear")
  // this should perform the search from the word clear; it is this step that
  // you call bfs.
   // compute a path from 'clear' to 'vague', based on the bfs outcome.
  println(f("vague"))

}
