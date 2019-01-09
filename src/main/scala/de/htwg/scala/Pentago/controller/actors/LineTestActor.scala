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
      var playerNumber = line(beginIndex)
      if (playerNumber != -1 && line.length - beginIndex >= 5) {
        for (y <- beginIndex until line.length) {
          if (playerNumber != line(y)) {
            playerNumber = -1
          }
        }
      }
      sender() ! LineWinnerMessage(playerNumber)  // Send message back
    case _ => log.warning("Received unknown message")
  }
}
