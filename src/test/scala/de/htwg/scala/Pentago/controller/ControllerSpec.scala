package de.htwg.scala.Pentago.controller

import de.htwg.scala.Pentago.model.GameField
import org.scalatest.Matchers._
import org.scalatest._

class ControllerSpec extends FlatSpec {


  "GameField" should "rotate upper left tile left" in {
    val controller = new Controller("P0", "P1")

    val placed = controller.placeOrb(0, 1, 2)
    placed should be (true)
    controller.rotate(0, 'r')

    val placedTwice = controller.placeOrb(1, 2, 2)
    placedTwice should be(false)
  }

  "Player 0" should "not be able to place orb twice" in {
    val controller = new Controller("P0", "P1")

    controller.placeOrb(1, 2, 0) should be(true)
    controller.placeOrb(1,2,0) should be(false)
  }

  "Player 0" should "begin game" in {
    val controller = new Controller("Player 0", "Player 1")

    controller.getCurrentPlayerIndex should be(0)
  }

  "Player 1" should "be second to place stone" in {
    val controller = new Controller("Player 0", "Player 1")

    controller.switchPlayer()
    controller.getCurrentPlayerIndex should be(1)
  }

  "Player 0" should "be third to place stone" in {
    val controller = new Controller("Player 0", "Player 1")

    controller.switchPlayer()
    controller.switchPlayer()
    controller.getCurrentPlayerIndex should be(0)
  }


  "Player 0" should "win with one horizontal line" in {
    val controller = new Controller("Player 1", "Player 2")
    for (y <- 0 until 5)
      controller.placeOrb(0, y, 0)
    controller.testWin() should contain(0)
    controller.testWin() should not contain 1
  }

  "Player 1" should "win with one vertical line" in {
    val controller = new Controller("Player 1", "Player 2")
    for (x <- 0 until 5)
      controller.placeOrb(x, 0, 1)
    controller.testWin() should contain(1)
    controller.testWin() should not contain 0
  }

  "Player 0" should "win with one diagonal line" in {
    val controller = new Controller("Player 1", "Player 2")
    for (x <- 0 until 5)
      controller.placeOrb(x, x, 0)
    controller.testWin() should contain(0)
    controller.testWin() should not contain 1
  }

  "Both Players" should "have win condition" in {
    val controller = new Controller("Player 1", "Player 2")
    for (x <- 0 until 5) {
      controller.placeOrb(x, 2, 0)
      controller.placeOrb(x, 1, 1)
    }
    controller.testWin() should contain(0)
    controller.testWin() should contain(1)
  }

  "No Players" should "have win condition" in {
    val controller = new Controller("Player 1", "Player 2")
    for (x <- 0 until 2) {
      controller.placeOrb(x, 2, 0)
      controller.placeOrb(x, 1, 1)
    }
    controller.testWin() should not contain 0
    controller.testWin() should not contain 1
  }

  "A Controller" should "return same GameField data" in {
    val controller = new Controller("1", "2")

    val gf_0 = new GameField(controller.getGameFiled)
    val gf_1 = new GameField(controller.getAllTiles)

    controller.gameField should equal(gf_0)
    controller.gameField should equal(gf_1)
  }

  "An orb" should "be where orb set" in {
    val controller = new Controller("1", "2")

    controller.placeOrb(1,2,1)

    controller.orbAt(1,2) should be(1)
  }
}
