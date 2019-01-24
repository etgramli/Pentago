package de.htwg.scala.Pentago.model

import org.scalatest._
import Matchers._

class TileSpec extends FlatSpec {
  "A Tile" should "rotate left" in {
    val original = new Tile(Array(
        Array( 1, 1, 1),
        Array(-1,-1, 2),
        Array(-1,-1, 2)))
    val expected = new Tile(Array(
      Array(1, 2, 2),
      Array(1,-1,-1),
      Array(1,-1,-1)
    ))

    val rotated = original.rotateLeft()

    rotated.userOccupation should equal (expected.userOccupation)
  }

  "A Tile" should "rotate right" in {
    val original = new Tile(Array(
      Array( 1, 1, 1),
      Array(-1,-1, 2),
      Array(-1,-1, 2)))
    val expected = new Tile(Array(
      Array(-1,-1, 1),
      Array(-1,-1, 1),
      Array( 2, 2, 1)
    ))

    val rotated = original.rotateRight()

    rotated.userOccupation should equal (expected.userOccupation)
  }

  "A Tile" should "have one Orb of Player 1" in {
    val original = new Tile()
    val expected = new Tile(Array(
      Array(-1, -1, -1),
      Array(-1, -1,  1),
      Array(-1, -1, -1)
    ))

    val placed = original.placeOrb(1, 2, 1)

    placed.userOccupation should equal(expected.userOccupation)
  }

  "A Player" should "not be able to override orb" in {
    val original = new Tile(Array(
      Array(-1, -1, -1),
      Array(-1, -1,  1),
      Array(-1, -1, -1)
    ))

    val placed = original.placeOrb(1, 2, 2)

    placed should equal(original)
  }

  "Four times left" should "be no rotation" in {
    val fourLeft = "llll"
    val reducedLeft = (new Tile).reduceRotateString(fourLeft)

    reducedLeft should have size 0
  }

  "Four times right" should "be no rotation" in {
    val fourRight = "rrrr"
    val reducedRight = (new Tile).reduceRotateString(fourRight)

    reducedRight should have size 0
  }

  "Six times left and one time right" should "be once left" in {
    val fourLeft = "lllllrl"
    val reducedLeft = (new Tile).reduceRotateString(fourLeft)

    reducedLeft should equal("l")
  }

  "Six times right and one time left" should "be once right" in {
    val fourRight = "rrrrrlr"
    val reducedRight = (new Tile).reduceRotateString(fourRight)

    reducedRight should equal("r")
  }

  "Three times right" should "be one time left" in {
    val threeTimesRight = "rrr"
    val oneTimeLeft = (new Tile).reduceRotateString(threeTimesRight)

    oneTimeLeft should equal("l")
  }

  "Three Left One Right" should "rotate two times left" in {
    val leftRight = "llrl"
    val noRotate = (new Tile).reduceRotateString(leftRight)

    noRotate should equal("ll")
  }
}
