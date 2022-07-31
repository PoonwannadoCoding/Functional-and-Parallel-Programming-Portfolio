import scala.annotation.tailrec

object GraphBFS extends App {

def nbrs(node: String): Set[String] = {
  if node.equals("A") then Set("B","C")
  else if node.equals("B") then Set("A","D")
  else if node.equals("C") then Set("A","E")
  else if node.equals("D") then Set("B","F")
  else if node.equals("E") then Set("C","F")
  else if node.equals("F") then Set("D", "E")
  else Set()
}


println(bfs(nbrs, "D"))



  def bfs[V](nbrs: V => Set[V], src: V): (Map[V, V], Map[V, Int]) = {

    def expand(frontier: Set[V], parent: Map[V, V]): (Set[V], Map[V, V]) = {
     val newFrontier: Set[V] = frontier.flatMap(nbrs(_).filterNot(parent.contains)) // find เพื่อนบ้าน then remove what already traverse

     @tailrec
     def helper(frontier: Set[V], result: Map[V,V]): Map[V,V] = {

      if frontier.isEmpty then result
      else {
        val newParent: Map[V,V] = result ++ nbrs(frontier.head).filterNot(parent.contains).foldLeft(Map.empty[V,V]) { (map, V)=> map + (V -> frontier.head)}
        helper(frontier.tail, newParent)
      }
     }

      (newFrontier, helper(frontier, parent))

    }

    @tailrec
    def iterate(frontier: Set[V], parent: Map[V, V], distance: Map[V, Int], d: Int): (Map[V, V], Map[V, Int]) =

      if frontier.isEmpty then (parent, distance)
      else {
        val (frontier_, parent_) = expand(frontier, parent)
        val distance_ = distance ++ frontier.foldLeft(Map[V, Int]()){(map, V) => map + (V -> d)}
        iterate(frontier_, parent_, distance_, d + 1)
      }

    iterate(Set(src), Map(src -> src), Map(), 0)
  }

}
