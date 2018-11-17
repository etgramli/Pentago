package de.htwg.scala.Pentago.model

import org.scalatest._
import Matchers._

class GameFieldSpec extends FlatSpec {

  "A GameFiled" should "rotate one Tile" in {
    // ToDo: Test rotate
  }


  "A GameField" should "have an Orb if placed" in {
    val original = new GameField()
    val placed = original.placeOrb(0, 1, 2)
    original.orbAt(0, 1) should be(-1)
    placed.orbAt(0, 1) should be(2)
  }
}
