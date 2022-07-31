import scala.io.Source
import scala.concurrent.Future
import java.util.concurrent.ConcurrentSkipListSet
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable
import scala.language.postfixOps

object TopK {

  def countWords(text: String): mutable.Map[String, Int] = {
    val counts = mutable.Map.empty[String, Int].withDefaultValue(0)
    text.split("[ ,!.]+").foreach{
      word =>{
        val lowWord = word.toLowerCase
        counts(lowWord) += 1
      }
    }
    counts
  }

  def mergeEm(map1: mutable.Map[String,Int], map2: mutable.Map[String,Int]): mutable.Map[String,Int] = {
    map1 ++ map2.map{ case (k,v) => k -> (v + map1.getOrElse(k,0)) }
  }


  def topKWords(k: Int)(fileSpec: String): Vector[(String, Int)] = {
    implicit val executionContext: ExecutionContext = ExecutionContext.global
    var lines = List("")
    val bufferedSource = Source.fromFile(fileSpec)
    for (line <- bufferedSource.getLines) {
      val l = line.toLowerCase
      lines = l :: lines
    }
    bufferedSource.close

    lines = lines.filter(i => i.nonEmpty)

    val handles = lines.map(line => Future{
      countWords(line)
    })

    var allAns = mutable.Map.empty[String, Int].withDefaultValue(0)
    val result = Future.sequence(handles).map(ansList => ansList.map {
      ans =>
        allAns = mergeEm(allAns, ans)
    })
    Await.result(result, Duration.Inf)
    val finalAns = allAns.toVector.sortBy(i => (-i._2,i._1))
    finalAns.take(k)

  }


  def main(args: Array[String]) = {
    val t = topKWords(10)("/home/worawit/Documents/GitHub/a3-starter/thesaurus_db.txt")
    println(t)
  }
}
