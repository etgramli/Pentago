package de.htwg.scala.Pentago.controller

import de.htwg.scala.Pentago.model.{GameField, Player, Tile}


class Controller(var gameField: GameField, val players: Array[Player]) {
  // 'l' links, 'r' rechts
  def rotate(tileNumber: Int, direction: Char): Unit = {
    this.gameField = gameField.rotate(tileNumber, direction)
  }

  def placeOrb(xCoord: Int, yCoord: Int, playerNumber: Int): Unit = {
    this.gameField = gameField.placeOrb(xCoord, yCoord, playerNumber)
  }

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
}
