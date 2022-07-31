import scala.collection.mutable
import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
object DataCrunch {

  trait DataProvider {
    def get(onSuccess: Seq[String] => Unit,
            onFailure: () => Unit): Unit
  }


  object LoremIpsum extends DataProvider {
    override def get(onSuccess: Seq[String] => Unit,
            onFailure: () => Unit): Unit = {
      val lorem =
        """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        Cras nec sagittis justo. Nullam dignissim ultricies velit a tempus.
        Aenean pharetra semper elit eu luctus. Fusce maximus lacus eget magna
        ultricies, ac suscipit justo lobortis. Nullam pellentesque lectus
        at tellus gravida rhoncus. Nam augue tortor, rutrum et eleifend id,
        luctus ut turpis. Vivamus nec sodales augue.

        Suspendisse non erat diam. Mauris hendrerit neque at sem laoreet
          vehicula. Sed aliquam urna a efficitur tincidunt. In non purus
        tincidunt, molestie velit vulputate, mollis nisl. Pellentesque
        rhoncus molestie bibendum. Etiam sit amet felis a orci fermentum
        tempor. Duis ante lacus, luctus ac scelerisque eget, viverra ut felis."""
      onSuccess(lorem.split("\n"))
    }
  }

  object FailedSample extends DataProvider {
    override def get(onSuccess: Seq[String] => Unit,
                     onFailure: () => Unit): Unit = {
      onFailure()
    }
  }

  // This returns a DataProvider that feeds the "consumer" all the lines from a
  // file indicated by filename.
  def FileSource(filename: String): DataProvider = new DataProvider {
    override def get(onSuccess: Seq[String] => Unit, onFailure: () => Unit): Unit = {
      try {
        val lines = io.Source.fromFile(filename)
          .getLines
          .toVector
        onSuccess(lines)


      }
      catch {
        case _: Throwable => onFailure()
      }
    }
  }

  def dataProviderFuture(dp: DataProvider): Future[Seq[String]] = {
    val sucess = Promise[Seq[String]]
      try{
        dp.get(line => {sucess.success(line)}, funcOnFailure)
      } catch {
        case ex => sucess.failure(ex)
      }
    def funcOnFailure() = println("Failed")
    sucess.future
  }

  def highestFreq(linesFut: Future[Seq[String]]): Future[(String, Double)] = {
    val result = Promise[(String, Double)]
    val splitWord = linesFut.value.get.get.flatMap(x => x.split("\\s+").map(_.replaceAll("[^A-Za-z0-9]", "")).filter(_.nonEmpty))
    val freq = splitWord.groupBy(x => x).map(y => (y._1, y._2.length)).maxBy(x => x._2)
    try{
      result.success((freq._1, freq._2/splitWord.size.toDouble))
    }
    result.future

  }

  def main(args: Array[String]) = {
    // Example of how DataProvider is typically used. Comment out the block of code
    // below so it doesn't print some random garbage.
    val sampleProvider = LoremIpsum
    val result = dataProviderFuture(sampleProvider)
    println(result)
    val yeet = highestFreq(result)
    Await.result(yeet,Duration.Inf)
    println(yeet)
  }

}
