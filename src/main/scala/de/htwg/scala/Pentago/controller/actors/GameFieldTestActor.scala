package de.htwg.scala.Pentago.controller.actors

import java.util.UUID

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import akka.util.Timeout
import de.htwg.scala.Pentago.model.GameField

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


object GameFieldTestActor {
  def props(): Props = Props(new GameFieldTestActor())
}
class GameFieldTestActor extends Actor {
  implicit val timeout: Timeout = Timeout(1 second)
  implicit val ec: ExecutionContext = ExecutionContext.global

  val log = Logging(context.system, this)
  private val lineTestActor = context.actorOf(LineTestActor.props(), "line-"+UUID.randomUUID())
  private val winners = scala.collection.mutable.Set[Int]()

  var resultReceiver: ActorRef = self
  var number: Int = 0

  override def receive: PartialFunction[Any, Unit] = {
    case TestGameFieldMessage(gf) =>
      number = 0
      resultReceiver = sender()

      winners --= winners
      for (x <- 0 until gf.size) {
        val line = getHorizontalRow(gf, x)
        for (y <- 0 until 2) {
          lineTestActor ! LineMessage(line, y)
          number += 1
        }
      }
      for (x <- 0 until 2; y <- 0 until 2) {
        val line = getDiagonalRow(gf, x, y)
        lineTestActor ! LineMessage(line, y)
        number += 1
      }
    case LineWinnerMessage(playerNumber: Int) =>
      if (playerNumber != -1)
        winners += playerNumber
      number -= 1
      if (number == 0) {
        var copiedSet = scala.collection.immutable.Set[Int]()
        copiedSet ++= winners
        resultReceiver ! GameFieldWinnersMessage(copiedSet)
      }
    case _ => log.warning("Received unknown message")
  }

  def getHorizontalRow(gf: GameField, x: Int): Array[Int] = {
    val row = Array.ofDim[Int](gf.size)
    for (y <- 0 until gf.size) {
      row(y) = gf.orbAt(x, y)
    }
    row
  }
  def getDiagonalRow(gf: GameField, xStart: Int, yStart: Int): Array[Int] = {
    val row = Array.ofDim[Int](gf.size)
    for (count <- 0 until 5) {
        row(count) = if (xStart + count >= gf.size || yStart + count >= gf.size) -1 else gf.orbAt(xStart + count, yStart + count)
    }
    row
  }
}
