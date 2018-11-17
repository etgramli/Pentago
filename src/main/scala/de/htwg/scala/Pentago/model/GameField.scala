package de.htwg.scala.Pentago.model

class GameField(val tiles: Array[Array[Tile]]) {
  val size = 6

  def this() {
    this(Array(
      Array(new Tile, new Tile),
      Array(new Tile, new Tile)
    ))
  }

  def orbAt(xCoord: Int, yCoord: Int): Int = {
    assert(xCoord < size && yCoord < size)
    val xTileNumber = xCoord / 3
    val yTileNumber = yCoord / 3
    val xTileCoord = xCoord % 3
    val yTileCoord = yCoord % 3

    tiles(xTileNumber)(yTileNumber).getOrb(xTileCoord,yTileCoord)
  }

  def placeOrb(xCoord: Int, yCoord: Int, playerNumber: Int): GameField = {
    assert(xCoord < size && yCoord < size)
    val xTileNumber = xCoord / 3
    val yTileNumber = yCoord / 3
    val xTileCoord = xCoord % 3
    val yTileCoord = yCoord % 3
    (xTileNumber, yTileNumber) match {
      case (0, 0) => return new GameField(Array(
        Array(tiles(0)(0).placeOrb(xTileCoord, yTileCoord, playerNumber), tiles(0)(1)),
        Array(tiles(1)(0),                                                tiles(1)(1))
      ))
      case (0, 1) => return new GameField(Array(
        Array(tiles(0)(0), tiles(0)(1).placeOrb(xTileCoord, yTileCoord, playerNumber)),
        Array(tiles(1)(0), tiles(1)(1))
      ))
      case (1, 0) => return new GameField(Array(
        Array(tiles(0)(0),                                                tiles(0)(1)),
        Array(tiles(1)(0).placeOrb(xTileCoord, yTileCoord, playerNumber), tiles(1)(1))
      ))
      case (1, 1) => return new GameField(Array(
        Array(tiles(0)(0), tiles(0)(1)),
        Array(tiles(1)(0), tiles(1)(1).placeOrb(xTileCoord, yTileCoord, playerNumber))
      ))
      case _ => return this
    }
    this
  }

  def rotate(tileNumber: Int, direction: Char): GameField = {
    tileNumber match {
      case 0 => return new GameField(Array(
        Array(tiles(0)(0).rotate(direction), tiles(0)(1)),
        Array(tiles(1)(0),                   tiles(1)(1)),
      ))
      case 1 => return new GameField(Array(
        Array(tiles(0)(0),                   tiles(0)(1).rotate(direction)),
        Array(tiles(1)(0),                   tiles(1)(1)),
      ))
      case 2 => return new GameField(Array(
        Array(tiles(0)(0),                   tiles(0)(1)),
        Array(tiles(1)(0).rotate(direction), tiles(1)(1)),
      ))
      case 3 => return new GameField(Array(
        Array(tiles(0)(0),                   tiles(0)(1)),
        Array(tiles(1)(0),                   tiles(1)(1).rotate(direction)),
      ))
      case _ => return this
    }
    this
  }
}
