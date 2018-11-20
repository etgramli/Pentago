package de.htwg.scala.Pentago.controller

import de.htwg.scala.Pentago.model.{GameField, Player, Tile}


class Controller(var gameField: GameField, val players: Array[Player]) {

  def this(playerOneName: String, playerTwoName: String) {
    this(new GameField(), Array(new Player(0, playerOneName), new Player(1, playerTwoName)))
  }


  // 'l' links, 'r' rechts
  def rotate(tileNumber: Int, direction: Char): Unit = {
    this.gameField = gameField.rotate(tileNumber, direction)
  }

  def placeOrb(xCoord: Int, yCoord: Int, playerNumber: Int): Unit = {
    this.gameField = gameField.placeOrb(xCoord, yCoord, playerNumber)
  }

  /*
  def getAllTiles(): Array[Array[Tile]] = {
    gameField.tiles
  }

  def getGameFiled(): Array[Array[Int]] = {
    val gameFieldData = Array.ofDim[Int](gameField.size, gameField.size)
    for (x <- 0 until gameField.size) {
      for (y <- 0 until gameField.size) {
        gameFieldData(x)(y) = gameField.orbAt(x, y)
      }
    }
    gameFieldData
  }

  // Test win condition (-1: Nobody won yet, else: playerNumber)
  def testWin(): Int = {
    var playerNumber = -1

    playerNumber = testWin(gameField)
    if (playerNumber != -1)
      return playerNumber
    playerNumber = testWin(gameField.rotateGameFieldLeft())
    if (playerNumber != -1)
      playerNumber
    else
      -1
  }

  def testWin(gameField: GameField): Int = {
    var playerNumber = -1
    for (x <- 0 until gameField.size) {
      for (y <- 0 until 2) {
        playerNumber = testHorizontalRow(x, y, gameField)
        if (playerNumber != -1) {
          return playerNumber
        }
      }
    }
    for (x <- 0 until 2) {
      for (y <- 0 until 2) {
        playerNumber = testDiagonalRow(x, y, gameField)
        if (playerNumber != -1) {
          return playerNumber
        }
      }
    }
    -1
  }

  def testHorizontalRow(xStart: Int, yStart: Int, gameField: GameField): Int = {
    val playerNumber = gameField.orbAt(xStart, yStart)
    if (playerNumber == -1)
      return playerNumber
    for (y <- yStart until yStart + 5) {
      if (yStart + y >= gameField.size || playerNumber != gameField.orbAt(xStart, yStart + y)) {
        return -1
      }
    }
    playerNumber
  }

  def testDiagonalRow(xStart: Int, yStart: Int, gameField: GameField): Int = {
    val playerNumber = gameField.orbAt(xStart, yStart)
    if (playerNumber == -1) {
      return playerNumber
    }
    for (x <- 0 until 5) {
      if (xStart + x >= gameField.size || yStart + x >= gameField.size || gameField.orbAt(xStart + x, yStart + x) != playerNumber) {
        return -1
      }
    }
    playerNumber
  }
  */
}
