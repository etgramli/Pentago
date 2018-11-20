package de.htwg.scala.Pentago.controller

import org.scalatest._
import Matchers._

class ControllerSpec extends FlatSpec {

  "Player 1" should "win with one horizontal line" in {
    val controller = new Controller("Player 1", "Player 2")
    for (y <- 0 until 6)
      controller.placeOrb(0, y, 0)
    controller.testWin() should be(0)
  }

  "Player 2" should "win with one diagonal line" in {
    val controller = new Controller("Player 1", "Player 2")
    for (x <- 0 until 6)
      controller.placeOrb(x, 0, 1)
    controller.testWin() should be(1)
  }

  "Player 1" should "win with one vertical line" in {
    val controller = new Controller("Player 1", "Player 2")
    for (x <- 0 until 6)
      controller.placeOrb(x, x, 0)
    controller.testWin() should be(0)
  }
}