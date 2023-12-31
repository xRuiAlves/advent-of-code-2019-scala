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

    val part1 = bfs(map, start, numKeys)
    val part2 = 0

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class VisitNode(coord: Coord2D, keys: Set[Char])

  case class BfsNode(visitNode: VisitNode, depth: Int)

  private val progress = mutable.Set[Int]()

  def bfs(map: Mat2D, start: Coord2D, numKeys: Int): Int = {
    val visited = mutable.Set[VisitNode]()
    val toVisit = mutable.Queue[BfsNode]()
    toVisit.enqueue(BfsNode(VisitNode(start, Set()), 0))

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

        getNeighbors(map, curr.visitNode.coord).foreach(neighbor => {
          val cell = map(neighbor._1)(neighbor._2)
          if (!cell.isUpper || curr.visitNode.keys.contains(cell.toLower)) {
            val keys = getKeys(curr.visitNode.keys.incl(cell))
            toVisit.enqueue(BfsNode(VisitNode(neighbor, keys), curr.depth + 1))
          }
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
}
