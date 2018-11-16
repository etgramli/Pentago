package de.htwg.scala.Pentago.model

import org.scalatest.FlatSpec


class TileSpec extends FlatSpec {
  "A Tile" should "rotate left" in {
    val original = new Tile(Array(Array(1,1,1),
        Array(0,0,0),
        Array(0,0,0)))
    //val rotated = original.rotateLeft()
  }
}
