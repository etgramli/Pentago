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
}
