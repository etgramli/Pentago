package de.htwg.scala.Pentago.model

object Tile {
  val size = 3
}
class Tile (val userOccupation: Array[Array[Int]]) {
  def this() {
    this(Array(
      Array(-1,-1,-1),
      Array(-1,-1,-1),
      Array(-1,-1,-1)))
  }

  def placeOrb(xCoord: Int, yCoord: Int, playerNum: Int): Tile = {
    if (userOccupation(xCoord)(yCoord) != -1)
      return this
    val occupation = this.userOccupation.transpose.transpose[Int]
    occupation(xCoord)(yCoord) = playerNum
    new Tile(occupation)
  }

  def getOrb(xCoord: Int, yCoord: Int): Int = {
    userOccupation(xCoord)(yCoord)
  }

  def rotate(direction: Char): Tile = {
    direction match {
          case 'l' => return rotateLeft()
          case 'r' => return rotateRight()
          case  _  => return this
    }
    this
  }

  def rotateLeft(): Tile = {
    new Tile(Array(
      Array(userOccupation(0)(2), userOccupation(1)(2), userOccupation(2)(2)),
      Array(userOccupation(0)(1), userOccupation(1)(1), userOccupation(2)(1)),
      Array(userOccupation(0)(0), userOccupation(1)(0), userOccupation(2)(0))))
  }

  def rotateRight(): Tile = {
    new Tile(Array(
      Array(userOccupation(2)(0), userOccupation(1)(0), userOccupation(0)(0)),
      Array(userOccupation(2)(1), userOccupation(1)(1), userOccupation(0)(1)),
      Array(userOccupation(2)(2), userOccupation(1)(2), userOccupation(0)(2))))
  }

  def reduceRotateString(str: String): String = {
    val rotationsForFullCycle = 4
    val left = str.count(_ == 'l') % rotationsForFullCycle
    val right = str.count(_ == 'r') % rotationsForFullCycle

    // Because 3 times left is one time right, and vice versa
    if (Math.abs(left - right) == 3) {
      return if (right > left) "l" else "r"
    }
    val direction = if (left > right) "l" else "r"
    direction * Math.abs(left - right)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Tile]

  override def equals(other: Any): Boolean = other match {
    case that: Tile =>
      (that canEqual this) &&
        userOccupation.deep == that.userOccupation.deep
    case _ => false
  }
}
