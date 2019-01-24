package de.htwg.scala.Pentago.model

import org.scalatest.Matchers._
import org.scalatest._

class GameFieldSpec extends FlatSpec {

  "GameField" should "rotate upper left tile left" in {
    val gf = new GameField()

    val placed = gf.placeOrb(0, 1, 2)
    val rotated = placed.rotate(0, 'r')

    rotated.orbAt(1,2) should be(2)
  }

  "A GameField" should "only accept only one orb per position" in {
    val gf = new GameField()

    val placed = gf.placeOrb(2, 2, 0)
    val placedAgain = placed.placeOrb(2,2,1)

    placedAgain.orbAt(2, 2) should be(0)
    placedAgain should equal(placed)
  }

  "A GameFiled" should "rotate one Tile" in {
    val original = new GameField()
    val placed = original.placeOrb(0, 1, 2)
    val rotated = placed.rotate(0, 'r')
    rotated.orbAt(1, 2) should be(2)
  }


  "A GameField" should "have an Orb if placed" in {
    val original = new GameField()
    val placed = original.placeOrb(0, 1, 2)
    original.orbAt(0, 1) should be(-1)
    placed.orbAt(0, 1) should be(2)
  }

  "A GameField" should "rotate left" in {
    /*
    Original:
    1 1 1 1 1 2
    0 0 0 0 0 2
    0 0 0 0 0 2
    0 0 0 0 0 2
    0 0 0 0 0 2
    0 0 0 0 0 2
     */
    val original = new GameField(Array(
      Array(new Tile(Array(Array(1, 1, 1), Array(0, 0, 0), Array(0, 0, 0))), new Tile(Array(Array(1, 1, 2), Array(0, 0, 2), Array(0, 0, 2)))),
      Array(new Tile(Array(Array(0, 0, 0), Array(0, 0, 0), Array(0, 0, 0))), new Tile(Array(Array(0, 0, 2), Array(0, 0, 2), Array(0, 0, 2))))
    ))
    val expected = new GameField(Array(
      Array(new Tile(Array(Array(2, 2, 2), Array(1, 0, 0), Array(1, 0, 0))), new Tile(Array(Array(2, 2, 2), Array(0, 0, 0), Array(0, 0, 0)))),
      Array(new Tile(Array(Array(1, 0, 0), Array(1, 0, 0), Array(1, 0, 0))), new Tile(Array(Array(0, 0, 0), Array(0, 0, 0), Array(0, 0, 0))))
    ))
    val rotated = original.rotateGameFieldLeft()
    rotated should be(expected)
  }

  "A GameField" should "be the same after converting to Array" in {
    val original = new GameField()
    val copy = new GameField(original.getGameFiled)

    copy should equal(original)
  }


  "A GameField" should "rotate all Tiles left" in {
    /*
    Original:
    1 1 1 1 1 2
    0 0 0 0 0 2
    0 0 0 0 0 2
    0 0 0 0 0 2
    0 0 0 0 0 2
    0 0 0 0 0 2
     */
    val original = new GameField(Array(
      Array(new Tile(Array(Array(1, 1, 1), Array(0, 0, 0), Array(0, 0, 0))), new Tile(Array(Array(1, 1, 2), Array(0, 0, 2), Array(0, 0, 2)))),
      Array(new Tile(Array(Array(0, 0, 0), Array(0, 0, 0), Array(0, 0, 0))), new Tile(Array(Array(0, 0, 2), Array(0, 0, 2), Array(0, 0, 2))))
    ))
    val expected = new GameField(Array(
      Array(original.tiles(0)(0).rotateLeft(),original.tiles(0)(1).rotateLeft()),
      Array(original.tiles(1)(0).rotateLeft(), original.tiles(1)(1).rotateLeft())
    ))
    val rotated = original.rotate(0, 'l')
      .rotate(1, 'l')
      .rotate(2, 'l')
      .rotate(3, 'l')
    rotated should equal(expected)
  }

  "A GameField" should "be the same after converting to array" in {
    val original = new GameField()
    val copy = new GameField(original.getGameFiled)

    copy should equal(original)
  }

  "A GameFiels" should "not equal another type" in {
    val gameField = new GameField()
    val tile = new Tile()

    gameField should not equal tile
  }

  "A GameField" should "not rotate a Tile if invalid input" in {
    val gameField = new GameField()
    gameField.placeOrb(0,1,1)

    val notRotated = gameField.rotate(5, 'l')

    gameField should be (notRotated)
  }
}
