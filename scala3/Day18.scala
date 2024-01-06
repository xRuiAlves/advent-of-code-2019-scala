//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.collection.mutable

object Day18 {
  type Keys = Int
  type Mat2D = Array[Array[Char]]
  type Coord2D = (Int, Int)

  private[this] final val WallCell = '#'
  private[this] final val StartCell = '@'
  private[this] final val EmptyCell = '.'

  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day18.txt")
    val t0 = System.nanoTime
    val (part1, part2) = getSolution(input)
    val t1 = System.nanoTime
    val duration = (t1 - t0) / 1e6d
    println()
    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
    println(s"Duration: $duration ms")
  }

  def printMap(map: Mat2D): Unit = map.foreach(a => {
    a.foreach(b => print(s"$b "))
    println()
  })

  def getSolution(input: Array[String]): (Int, Int) = {
    val map = input.map(_.toCharArray)
    val start = parseStart(map)
    val targetKeys = map.flatten
      .filter(_.isLower)
      .map(getKeyId)
      .foldLeft(0)(getUpdatedKeys)

    val part1 = 0
    val startsPart2 = updateMapForPart2(map, start)
    val part2 = bfs(map, startsPart2, targetKeys)

    (part1, part2)
  }

  case class VisitNode(coord: Coord2D, keys: Keys) {
    private var help = keys
    private val keysStr = "abcdefghijklmnopqrstuvwxyz".filter(_ => {
      val t = (help % 2) != 0
      help /= 2
      t
    }).mkString

    override def toString: String = s"VisitNode($coord, $keysStr, $keys)"
  }

  case class BfsNode(coords: Array[Coord2D], keys: Keys, depth: Int, prev: Array[BfsNode] = Array.empty[BfsNode])

  def findChar(map: Mat2D, char: Char): Coord2D = {
    val i = map.indexWhere(_.contains(char))
    val j = map(i).indexOf(char)
    (i, j)
  }

  def kkeys(a: Array[Char]): Keys = a.map(getKeyId).foldLeft(0)(getUpdatedKeys)

  def bfs(map: Mat2D, starts: Array[Coord2D], targetKeys: Keys): Int = {
    val visited = starts.map(_ => mutable.Set[VisitNode]())
    val toVisit = mutable.Queue[BfsNode]()
    toVisit.enqueue(BfsNode(starts, 0, 0))

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()

      if (curr.depth == 1 && curr.coords(0) == findChar(map, 'e') && curr.keys == kkeys(Array('e'))) {
        val v = VisitNode(curr.coords(0), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 5 && curr.coords(1) == findChar(map, 'h') && curr.keys == kkeys(Array('e', 'h'))) {
        val v = VisitNode(curr.coords(1), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 7 && curr.coords(3) == findChar(map, 'i') && curr.keys == kkeys(Array('e', 'h', 'i'))) {
        val v = VisitNode(curr.coords(3), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 9 && curr.coords(0) == findChar(map, 'a') && curr.keys == kkeys(Array('e', 'h', 'i', 'a'))) {
        val v = VisitNode(curr.coords(0), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 10 && curr.coords(0) == findChar(map, 'b') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b'))) {
        val v = VisitNode(curr.coords(0), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      var a = false
      if (curr.depth == 16 && curr.coords(1) == findChar(map, 'c') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c'))) {
        val v = VisitNode(curr.coords(1), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
        a = true
      }

//      if (curr.depth == 17 && curr.coords(0) == )

      if (curr.depth == 18 && curr.coords(0) == findChar(map, 'd') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd'))) {
        val v = VisitNode(curr.coords(0), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 26 && curr.coords(0) == findChar(map, 'f') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd', 'f'))) {
        val v = VisitNode(curr.coords(0), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 36 && curr.coords(0) == findChar(map, 'g') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd', 'f', 'g'))) {
        val v = VisitNode(curr.coords(0), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 45 && curr.coords(3) == findChar(map, 'k') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd', 'f', 'g', 'k'))) {
        val v = VisitNode(curr.coords(3), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 46 && curr.coords(3) == findChar(map, 'j') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd', 'f', 'g', 'k', 'j'))) {
        val v = VisitNode(curr.coords(3), curr.keys)
        println(s"${curr.depth}: $v")
        println("---")
        curr.coords.foreach(println)
        println("---")
        println()
      }

      if (curr.depth == 47 && curr.coords(3) == (3, 10) && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd', 'f', 'g', 'k', 'j'))) {
        val v = VisitNode(curr.coords(3), curr.keys)
        println(s"${curr.depth}: $v")
      }

//      if ((curr.coords(1) == (3,9))) {
//        println()
//        println("###")
//        println(curr.coords(1))
//        println(curr.depth)
//        println()
//      }

//      if (curr.coords(1) == findChar(map, 'l') && curr.keys == kkeys(Array('e', 'h', 'i', 'a', 'b', 'c', 'd', 'f', 'g', 'k', 'j', 'l'))) {
//        val v = VisitNode(curr.coords(1), curr.keys)
//        println(s"${curr.depth}: $v")
//      }

      if (curr.keys == targetKeys) {
        return curr.depth
      }

      if (a) {
        println()
        println()
        println()
        println()
      }
      curr.coords.zipWithIndex.foreach { case (coord, i) =>
        val visitNode = VisitNode(coord, curr.keys)
        if (a) {
          println("###")
          println(visitNode)
        }
        if (!visited(i).contains(visitNode)) {

          if (visitNode == VisitNode((3, 3), 407)) {
            println("\n$$$$$$$$$$$$$$$$")
            println("$$$$$$$$$$$$$$$$")
            curr.prev.foreach(xx => {
              println(xx.depth)
              xx.coords.indices.foreach(i => {
                println(VisitNode(xx.coords(i), xx.keys))
              })
            })
            println(curr.depth)
            curr.coords.indices.foreach(i => {
              println(VisitNode(curr.coords(i), curr.keys))
            })
            println("$$$$$$$$$$$$$$$$")
            println("$$$$$$$$$$$$$$$$\n")
          }

          visited(i).addOne(visitNode)
          if (a) {
            println("Didn't contain! Let's visit it")
          }

          getNeighbors(map, coord).foreach(neighbor => {
            if (a) {
              println("\t" + neighbor)
            }
            val cell = map(neighbor._1)(neighbor._2)
            val keys =
              if (cell.isLower) getUpdatedKeys(curr.keys, getKeyId(cell))
              else curr.keys

            if (cell.isLower || cell == EmptyCell || (cell.isUpper && canOpenDoor(curr.keys, cell))) {
              toVisit.enqueue(BfsNode(curr.coords.updated(i, neighbor), keys, curr.depth + 1, curr.prev.appended(curr)))
            }
          })
        } else {
          if (a) {
            println("Already visited, skipping")
          }
        }
      }
      if (a) {
        println()
        println()
        println()
        println()
        a = false
      }
    }

    throw new Error("Solution not found!")
  }

  def getNeighbors(map: Mat2D, coord: Coord2D): Array[Coord2D] = coord match { case (i, j) =>
    Array((i - 1, j), (i + 1, j), (i, j - 1), (i, j + 1))
      .filter(coord => isInBounds(map, coord))
      .filter { case(i, j) => map(i)(j) != WallCell }
  }

  def isInBounds(map: Mat2D, coord: Coord2D): Boolean = coord match
    case (i, j) => i >= 0 && j >= 0 && i < map.length && j < map(i).length

  def parseStart(map: Mat2D): Coord2D = {
    val startI = map.indexWhere(_.contains(StartCell))
    val startJ = map(startI).indexOf(StartCell)
    (startI, startJ)
  }

  def getUpdatedKeys(keys: Keys, newKey: Int): Keys = keys | newKey

  def getKeyId(key: Char): Int = 1 << (key - 'a')

  def canOpenDoor(keys: Keys, door: Char): Boolean = (keys & getKeyId(door.toLower)) != 0

  def updateMapForPart2(map: Mat2D, start: Coord2D): Array[Coord2D] = start match { case (i, j) =>
    map(i - 1)(j - 1) = EmptyCell
    map(i - 1)(j + 1) = EmptyCell
    map(i + 1)(j - 1) = EmptyCell
    map(i + 1)(j + 1) = EmptyCell

    map(i)(j) = WallCell
    map(i - 1)(j) = WallCell
    map(i + 1)(j) = WallCell
    map(i)(j - 1) = WallCell
    map(i)(j + 1) = WallCell

    Array(
      (i - 1, j - 1),
      (i - 1, j + 1),
      (i + 1, j - 1),
      (i + 1, j + 1),
    )
  }
}
