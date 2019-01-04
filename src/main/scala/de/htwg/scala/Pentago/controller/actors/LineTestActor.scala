package de.htwg.scala.Pentago.controller.actors

import akka.actor.{Actor, Props}
import akka.event.Logging

object LineTestActor {
  def props(): Props = Props(new LineTestActor())
}
class LineTestActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: PartialFunction[Any, Unit] = {
    case LineMessage(line, beginIndex) =>
      val playerNumber = line(beginIndex)
      if (playerNumber == -1)
        sender() ! LineWinnerMessage(playerNumber) // Send message back
      for (y <- beginIndex until beginIndex + 5) {
        if (beginIndex + y >= line.length || playerNumber != line(beginIndex + y)) {
          sender() ! LineWinnerMessage(-1)        // Send message back
        }
      }
    case _ => log.warning("Received unknown message")
  }
}
