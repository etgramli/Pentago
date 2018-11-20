package de.htwg.scala.Pentago.controller

import org.scalatest._
import Matchers._
import de.htwg.scala.Pentago.model.{GameField, Player, Tile}

class ControllerSpec extends FlatSpec {

  val players = Array(new Player(0, "Player 0"),
    new Player(1, "Player 1"))

  "Player 1" should "win with one horizontal line" in {
    /*
    Original:
    1 1 1 1 1 1
    0 0 0 0 0 0
    0 0 0 0 0 0
    0 0 0 0 0 0
    0 0 0 0 0 0
    0 0 0 0 0 0
     */
    val original = new GameField(Array(
      Array(new Tile(Array(Array(1, 1, 1), Array(-1, -1, -1), Array(-1, -1, -1))), new Tile(Array(Array(1, 1, 1), Array(-1, -1, -1), Array(-1, -1, -1)))),
      Array(new Tile(Array(Array(-1, -1, -1), Array(-1, -1, -1), Array(-1, -1, -1))), new Tile(Array(Array(-1, -1, -1), Array(-1, -1, -1), Array(-1, -1, -1))))
    ))
    val controller = new Controller(original, players)
    controller.testWin() should be(1)
  }

  "Player 2" should "win with one diagonal line" in {
    /*
    Original:
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
     */
    val original = new GameField(Array(
      Array(new Tile(Array(Array(2, -1, -1), Array(-1, 2, -1), Array(-1, -1, 2))), new Tile(Array(Array(-1, -1, 1), Array(-1, -1, 1), Array(-1, -1, -1)))),
      Array(new Tile(Array(Array(-1, -1, -1), Array(-1, -1, -1), Array(-1, -1, -1))), new Tile(Array(Array(2, -1, -1), Array(-1, 2, -1), Array(-1, 2, -1))))
    ))
    val controller = new Controller(original, players)
    controller.testWin() should be(2)
  }

  "Player 1" should "win with one vertical line" in {
    /*
    Original:
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
    0 0 0 0 0 1
     */
    val original = new GameField(Array(
      Array(new Tile(Array(Array(-1, -1, -1), Array(-1, -1, -1), Array(-1, -1, -1))), new Tile(Array(Array(-1, -1, 1), Array(-1, -1, 1), Array(-1, -1, 1)))),
      Array(new Tile(Array(Array(-1, -1, -1), Array(-1, -1, -1), Array(-1, -1, -1))), new Tile(Array(Array(-1, -1, 1), Array(-1, -1, 1), Array(-1, -1, 1))))
    ))
    val controller = new Controller(original, players)
    controller.testWin() should be(1)
  }
}
