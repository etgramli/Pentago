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
    var left = str.count(_ == 'l')
    var right = str.count(_ == 'r')
    // Reduce 4 rotations in each direction, because 4 90° rotation does not change the state
    left = left % 4
    right = right % 4
    if (left > right) {
      // Return left - right 'l's
      val buf = new StringBuilder()
      while (left > right) {
        buf.append('l')
        left = left - 1
      }
      buf.toString()
    } else if (right > left) {
      // Return right - left 'r's
      val buf = new StringBuilder()
      while (right > left) {
        buf.append('r')
        right = right - 1
      }
      buf.toString()
    } else {
      ""
    }
  }
}
