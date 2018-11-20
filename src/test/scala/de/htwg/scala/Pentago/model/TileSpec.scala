package de.htwg.scala.Pentago.model

import org.scalatest._
import Matchers._

class TileSpec extends FlatSpec {
  "A Tile" should "rotate left" in {
    val original = new Tile(Array(
        Array(1,1,1),
        Array(0,0,2),
        Array(0,0,2)))
    val expected = new Tile(Array(
      Array(1,2,2),
      Array(1,0,0),
      Array(1,0,0)
    ))
    val rotated = original.rotateLeft()
    rotated.userOccupation should equal (expected.userOccupation)
  }

  "A Tile" should "rotate right" in {
    val original = new Tile(Array(
      Array(1,1,1),
      Array(0,0,2),
      Array(0,0,2)))
    val expected = new Tile(Array(
      Array(0,0,1),
      Array(0,0,1),
      Array(2,2,1)
    ))
    val rotated = original.rotateRight()
    rotated.userOccupation should equal (expected.userOccupation)
  }

  "A Tile" should "have one Orb of Player 1" in {
    val original = new Tile(Array(
      Array(0, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 0)
    ))
    val expected = new Tile(Array(
      Array(0, 0, 0),
      Array(0, 0, 1),
      Array(0, 0, 0)
    ))
    val placed = original.placeOrb(1, 2, 1)
    placed.userOccupation should equal(expected.userOccupation)
  }
}
