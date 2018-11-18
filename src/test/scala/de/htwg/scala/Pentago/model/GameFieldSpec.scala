package de.htwg.scala.Pentago.model

import org.scalatest._
import Matchers._

class GameFieldSpec extends FlatSpec {

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
}
