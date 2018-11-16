package de.htwg.scala.Pentago.model

class Tile (userOccupation: Array[Array[Int]]) {

  def this() {
    this(-1,-1,-1,
         -1,-1,-1,
         -1,-1,-1)
  }

  def getOrb(xCoord: Int, yCoord: Int): Int = {
    // ToDo
    return 0
  }

  def rotate(str: String): Tile = {
    val reducedString = reducedString(str)
    for (leftRight <- reducedString) {
      leftRight match {
        case 'l' => return rotateLeft()
        case 'r' => return rotateRight()
      }
    }
  }

  def rotateLeft(): Tile = {
    // ToDo
    return new Tile()
  }

  def rotateRight(): Tile = {
    var temp = rotateLeft()
    temp = rotateLeft()
    temp = rotateLeft()
    temp
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
