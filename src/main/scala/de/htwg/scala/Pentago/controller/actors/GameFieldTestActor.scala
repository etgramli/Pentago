package de.htwg.scala.Pentago.controller.actors

import akka.actor.{Actor, Props}
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout
import de.htwg.scala.Pentago.model.GameField

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


object GameFieldTestActor {
  def props(): Props = Props(new GameFieldTestActor())
}
class GameFieldTestActor() extends Actor {
  val log = Logging(context.system, this)
  val lineTestActor = context.actorOf(LineTestActor.props(), "line")
  var winners = Set[Int]()
  implicit val timeout: Timeout = Timeout(4 second)
  implicit val ec: ExecutionContext = ExecutionContext.global

  var waitForFuturesList: List[Future[Any]] = List[Future[Any]]()

  override def receive: PartialFunction[Any, Unit] = {
    case TestGameFieldMessage(gf) =>
      val waitForFuturesList = List[Future[Any]]()
      winners = Set[Int]()
      for (x <- 0 until gf.size) {
        val line = getHorizontalRow(gf, x)
        for (y <- 0 until 2) {
          // Create child actor to test line
          waitForFuturesList :+ lineTestActor ? LineMessage(line, y)
        }
      }
      for (x <- 0 until 2) {
        for (y <- 0 until 2) {
          val line = getDiagonalRow(gf, x, y)
          // Create child actor to test line
          waitForFuturesList :+ lineTestActor ? LineMessage(line, y)
        }
      }
      // Wait for all child actor messages to know who won
      val lineFutures = Future.sequence(waitForFuturesList)
      lineFutures.onComplete {
        case Success(e) => sender() ! GameFieldWinnersMessage(winners)
        case Failure(e) => log.warning("Failed to process all lines: ")
      }
      // Send winners back to caller
    case LineWinnerMessage(playerNumber: Int) =>
      winners += playerNumber
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
      if (xStart + count >= gf.size || yStart+count >= gf.size) {
        row(count) = -1
      } else {
        row(count) = gf.orbAt(xStart+count, yStart+count)
      }
    }
    row
  }
}
