import scala.annotation.tailrec

object Maze extends App {

  case class Pos(x :Int,y: Int)

  def solveMaze(maze: Vector[String]): Option[String] = {

    val path: (Map[Pos, Set[Pos]], Pos, Pos) = traverse(maze, Pos(0,0), Map.empty, Pos(0,0),Pos(0,0)) //10.966


    val getPaths = optimise(path)



    summonBfs(getPaths, path._2, path._3)

  }

  def summonBfs(path: Pos => Set[Pos], start: Pos, des: Pos): Option[String] = {

    if path(start).isEmpty then {
      None
    }
    else
      val map = GraphBFS.bfs(path, des)._1
      println("Yeet "+map)

      val pathPos = start +: mapToList(map, List(), des, start, map)

      Option(direction(pathPos.tail, pathPos.head, ""))
  }

  def optimise(save: (Map[Pos, Set[Pos]], Pos, Pos)): Pos => Set[Pos] = {

    val yeet = (key: Pos) => save._1.getOrElse(key, Nil).toSet

    yeet
  }


  def findNbr(maze: Vector[String], pos: Pos, area: List[Pos]): List[Pos] = area.size match {

    case 0 => Nil
    case 4 => if !maze(pos.y).charAt(pos.x + 1).toLower.toString.equals("x") then Pos(pos.x + 1, pos.y) :: findNbr(maze, pos, area.tail)
      else findNbr(maze, pos, area.tail)

    case 3 => if !maze(pos.y).charAt(pos.x - 1).toLower.toString.equals("x") then Pos(pos.x - 1, pos.y) :: findNbr(maze, pos, area.tail)
      else findNbr(maze, pos, area.tail)

    case 2 => if !maze(pos.y + 1).charAt(pos.x).toLower.toString.equals("x") then Pos(pos.x, pos.y + 1) :: findNbr(maze, pos, area.tail)
      else findNbr(maze, pos, area.tail)

    case 1 => if !maze(pos.y - 1).charAt(pos.x).toLower.toString.equals("x") then Pos(pos.x, pos.y - 1) :: findNbr(maze, pos, area.tail)
      else findNbr(maze, pos, area.tail)

}


  @tailrec
  def traverse(maze: Vector[String], current: Pos, rec: Map[Pos, Set[Pos]], start: Pos, end: Pos): (Map[Pos, Set[Pos]], Pos, Pos) = {
    val searchArea: List[Pos] = List(Pos(1,0), Pos(-1,0), Pos(0,1), Pos(0,-1))
    if current.y > maze.size-2 then (rec, start, end)
    else if current.x > maze.head.length-1 then
      traverse(maze, Pos(0, current.y+1), rec, start, end)
    else if (maze(current.y)).charAt(current.x).toLower.toString.equals(" ") then
      traverse(maze, Pos(current.x+1, current.y), rec + (current -> findNbr(maze, current, searchArea).toSet), start, end)
    else if maze(current.y).charAt(current.x).toLower.toString.equals("e") then
      traverse(maze, Pos(current.x+1, current.y), rec + (current -> findNbr(maze, current, searchArea).toSet), start, current)
    else if maze(current.y).charAt(current.x).toLower.toString.equals("s") then{
      traverse(maze, Pos(current.x+1, current.y), rec + (current -> findNbr(maze, current, searchArea).toSet), current, end)
    }
    else traverse(maze, Pos(current.x+1, current.y), rec, start, end)
  }



  @tailrec
  def mapToList( map: Map[Pos,Pos], result: List[Pos], start: Pos, des: Pos, collection: Map[Pos,Pos]): List[Pos] = {
    if map.tail.isEmpty then Nil
    else {
      if des.equals(start)  then result
      else {
        val newWord: Pos = collection.getOrElse(des,Pos(0,0))
        mapToList(map.tail, result :+ newWord, start, newWord,collection)
      }
    }
  }

  @tailrec
  def direction(path: List[Pos], prevPos: Pos, direc: String): String = {
    if path.isEmpty then direc
    else if prevPos.x == path.head.x - 1 && prevPos.y == path.head.y then direction(path.tail, path.head, direc + "r")
    else if prevPos.x == path.head.x + 1 && prevPos.y == path.head.y then direction(path.tail, path.head, direc + "l")
    else if prevPos.y == path.head.y - 1 && prevPos.x == path.head.x then direction(path.tail, path.head, direc + "d")
    else direction(path.tail, path.head, direc + "u")
  }

  val bigMaze: Vector[String] = Vector(
    "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
    "x                                                                                                                                                                                                                                                       ex",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                  v                                      x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                           x                                                                                                                                                                            x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                    x                                                                                                                                                                                                                   x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "x                                                                                                                                                                                                                                                        x",
    "xs                                                                                                                                                                                                                                                       x",
    "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
  )


  val maze: Vector[String] = Vector(
    "xxxxxxxxxxxxxxxxxx",
    "x   x       x   ex",
    "x   x    x  x xxxx",
    "x        x  x    x",
    "xs  x    x       x",
    "xxxxxxxxxxxxxxxxxx")







  val t1 = System.nanoTime
  println(solveMaze(maze))
  //println(solveMaze(bigMaze))
  val duration = (System.nanoTime - t1) / 1e9d
  println(duration)





}

