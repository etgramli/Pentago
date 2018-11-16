package de.htwg.scala.Pentago.model

class Tile (userOccupation: Array[Array[Int]]) {

  def this() {
    this(-1,-1,-1,
         -1,-1,-1,
         -1,-1,-1)
  }
/*
  def rotate(str: String): Tile = {
    val reducedString = reducedString(str)
    var temp: Tile
    for (var leftRight <- reducedString) {
      if (leftRight == 'l') {
        temp = rotateLeft()
      } else if (leftRight == 'r')
        temp = rotateRight()
    }
  }

  def rotateLeft(): Tile = {
    // ToDo
  }

  def rotateRight(): Tile = {
    var temp = rotateLeft()
    temp = rotateLeft()
    temp = rotateLeft()
    temp
  }
*/
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

class UserMatrix (data: Array[Array[Int]]) {
  def rotateLeft(): UserMatrix = {
    this(
    Array(
      Array(data(1)(3), data(2)(3), data(3)(3)),
      Array(data(1)(2), data(2)(2), data(3)(2)),
      Array(data(1)(1), data(2)(1), data(3)(1))
    ))
  }
  def rotateRight(): UserMatrix = {
    this(
      Array(
        Array(data(3)(1), data(2)(1), data(1)(1)),
        Array(data(3)(2), data(2)(2), data(1)(2)),
        Array(data(3)(3), data(2)(3), data(1)(3))
    ))
  }
}
