//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines
import scala.collection.mutable

object Day18 {
  type Mat2D = Array[Array[Char]]
  type Coord2D = (Int, Int)

  private[this] final val EmptyCell = '.'
  private[this] final val WallCell = '#'
  private[this] final val StartCell = '@'

  def main(args: Array[String]): Unit = {
    val map = readResourceLines("day18.txt").map(_.toCharArray)
    val start = parseStart(map)
    val numKeys = getKeys(map.flatten.toSet).size

    val part1 = bfs(map, Set(start), numKeys)
    val startsPart2 = updateMapForPart2(map, start)
    val part2 = bfs(map, startsPart2, numKeys)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  def printMap(map: Mat2D): Unit = map.foreach(a => {
    a.foreach(b => print(s"$b "))
    println()
  })

  case class VisitNode(coords: Set[Coord2D], keys: Set[Char])

  case class BfsNode(visitNode: VisitNode, depth: Int)

  private val progress = mutable.Set[Int]()

  def bfs(map: Mat2D, starts: Set[Coord2D], numKeys: Int): Int = {
    val visited = mutable.Set[VisitNode]()
    val toVisit = mutable.Queue[BfsNode]()
    toVisit.enqueue(BfsNode(VisitNode(starts, Set()), 0))

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()
      if (!progress.contains(curr.depth)) {
        progress.addOne(curr.depth)
        println(curr.depth)
      }

      if (!visited.contains(curr.visitNode)) {
        visited.add(curr.visitNode)

        if (curr.visitNode.keys.size == numKeys) {
          return curr.depth
        }

        val coords = curr.visitNode.coords
        coords.foreach(coord => {
          getNeighbors(map, coord).foreach(neighbor => {
            val cell = map(neighbor._1)(neighbor._2)
            if (!cell.isUpper || curr.visitNode.keys.contains(cell.toLower)) {
              val keys = getKeys(curr.visitNode.keys.incl(cell))
              toVisit.enqueue(BfsNode(VisitNode(coords - coord + neighbor, keys), curr.depth + 1))
            }
          })
        })
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

  def getKeys(elems: Set[Char]): Set[Char] = elems.filter(_.isLower)
  def getDoors(elems: Set[Char]): Set[Char] = elems.filter(_.isUpper)

  def updateMapForPart2(map: Mat2D, start: Coord2D): Set[Coord2D] = start match { case (i, j) =>
    map(i - 1)(j - 1) = StartCell
    map(i - 1)(j + 1) = StartCell
    map(i + 1)(j - 1) = StartCell
    map(i + 1)(j + 1) = StartCell

    map(i)(j) = WallCell
    map(i - 1)(j) = WallCell
    map(i + 1)(j) = WallCell
    map(i)(j - 1) = WallCell
    map(i)(j + 1) = WallCell

    Set(
      (i - 1, j - 1),
      (i - 1, j + 1),
      (i + 1, j - 1),
      (i + 1, j + 1),
    )
  }
}
