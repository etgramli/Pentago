package de.htwg.scala.Pentago.model

class GameField(val tiles: Array[Array[Tile]]) {

  def this() {
    this(Array(
      Array(new Tile, new Tile),
      Array(new Tile, new Tile)
    ))
  }

  def rotateRight(tileNumber: Int, direction: Char):  GameField = {
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
    return this
  }
}
