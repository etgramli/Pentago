package de.htwg.scala.Pentago.model


class GameField(val tiles: Array[Array[Tile]]){
  val size = 6

  def this() {
    this(Array(
      Array(new Tile, new Tile),
      Array(new Tile, new Tile)
    ))
  }

  def this(gfArray: Array[Array[Int]]) {
    this()
    for (x <- 0 until size; y <- 0 until size) {
      this.placeOrb(x,y,gfArray(x)(y))
    }
  }

  def getGameFiled: Array[Array[Int]] = {
    val gameFieldData = Array.ofDim[Int](size, size)
    for (x <- 0 until size; y <- 0 until size) {
      gameFieldData(x)(y) = orbAt(x, y)
    }
    gameFieldData
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
    if (orbAt(xCoord, yCoord) != -1)
      return this   // Do not change anything if place already occupied

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

  // Must only be used from Controller to test the win conditions
  def rotateGameFieldLeft(): GameField = {
    new GameField(Array(
      Array(tiles(0)(1).rotateLeft(), tiles(1)(1).rotateLeft()),
      Array(tiles(0)(0).rotateLeft(), tiles(1)(0).rotateLeft())
    ))
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[GameField]

  override def equals(other: Any): Boolean = other match {
    case that: GameField =>
      (that canEqual this) &&
        size == that.size &&
        tiles(0)(0) == that.tiles(0)(0) &&
        tiles(0)(1) == that.tiles(0)(1) &&
        tiles(1)(0) == that.tiles(1)(0) &&
        tiles(1)(1) == that.tiles(1)(1)
    case _ => false
  }
}
