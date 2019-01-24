package de.htwg.scala.Pentago.model

class Tile (val userOccupation: Array[Array[Int]]) {
  def this() {
    this(Array(
      Array(-1,-1,-1),
      Array(-1,-1,-1),
      Array(-1,-1,-1)))
  }


  def placeOrb(xCoord: Int, yCoord: Int, playerNum: Int): Tile = {
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
          case _ => return this
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
    rotateLeft().rotateLeft().rotateLeft() // Because three times left is right
  }

  def reduceRotateString(str: String): String = {
    // Reduce 4 rotations in each direction, because 4 90° rotation does not change the state
    val left = str.count(_ == 'l') % 4
    val right = str.count(_ == 'r') % 4
    if (left > right) {
      // Return left - right 'l's
      if (left - right == 3) {
        "r"
      } else {
        "l" * (left - right)
      }
    } else if (right > left) {
      // Return right - left 'r's
      if (right - left == 3) {
        "l"
      } else {
        "r" * (right - left)
      }
    } else {
      ""
    }
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Tile]

  override def equals(other: Any): Boolean = other match {
    case that: Tile =>
      (that canEqual this) &&
        userOccupation.deep == that.userOccupation.deep
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(userOccupation)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
