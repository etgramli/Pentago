package de.htwg.scala.Pentago.model

import java.util.UUID

class GameField {
    val tiles = Array(
      Array(new Tile, new Tile),
      Array(new Tile, new Tile)
    )


  def testWind(rowLength: Int): UUID = {
    // Execute testWin(Int, Int, Int) for all dots
    // if winner exists, return its UUID, else Nothing
    return new Nothing
  }

  def testWin(rowLength: Int, xPos: Int, yPos: Int): Boolean = {
    // ToDo
    // Test surrounding dots for placed pieces
      // Extend line and test if length is enough
        // return true
    return false
  }

  def getLength(xBegin: Int, yBegin: Int, xNext: Int, yNext: Int): Int = {
    var length = 1
    // ToDo
    // get direction of begin to next and count next ones
    val deltaX = xNext - xBegin
    val deltaY = yNext - yBegin

    // move
    while
    // compare if dots are from the same player

    return length
  }
}
