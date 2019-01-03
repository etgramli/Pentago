package de.htwg.scala.Pentago.controller.actors

import akka.actor.Actor
import akka.event.Logging
import de.htwg.scala.Pentago.model.GameField


class GameFieldTestActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: PartialFunction[Any, Unit] = {
    case TestGameFieldMessage(gf) =>
      var winners = Set[Int]()
      var playerNumber = -1
      for (x <- 0 until gf.size) {
        for (y <- 0 until 2) {
          // ToDo: Create child actor to test line
          //playerNumber = testHorizontalRow(x, y, gf)
          if (playerNumber != -1) {
            winners += playerNumber
          }
        }
      }
      for (x <- 0 until 2) {
        for (y <- 0 until 2) {
          // ToDo: Create child actor to tets line
          playerNumber = testDiagonalRow(x, y, gf)
          if (playerNumber != -1) {
            winners += playerNumber
          }
        }
      }
      // ToDo: Wait for all child actor messages to know who won
      winners
      // Send winners back to caller
    case _ => log.warning("Received unknown message")
  }

  def getHorizontalRow(gf: GameField, x: Int): Array[Int] = {
    val row = Array.ofDim[Int](gf.size)
    for (y <- 0 until gf.size) {
      row(y) = gf.orbAt(x, y)
    }
    row
  }
  def getDiagonalRow(gf: GameField, x: Int, y: Int): Array[Int] = {
    val row = Array.ofDim[Int](gf.size)
    for (count <- 0 until 5) {
      if (x + count >= gf.size || y+count >= gf.size) {
        row(count) = -1
      } else {
        row(count) = gf.orbAt(x+count, y+count)
      }
    }
    row
  }
}
