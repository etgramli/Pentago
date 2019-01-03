package de.htwg.scala.Pentago.controller.actors

import akka.actor.{Actor, ActorRef}
import akka.event.Logging


class LineTestActor(parentGameFieldActor: ActorRef) extends Actor {
  val log = Logging(context.system, this)

  override def receive: PartialFunction[Any, Unit] = {
    case LineMessage(line, beginIndex) =>
      val playerNumber = line(beginIndex)
      if (playerNumber == -1)
        parentGameFieldActor ! LineWinnerMessage(playerNumber) // Send message back
      for (y <- beginIndex until beginIndex + 5) {
        if (beginIndex + y >= line.length || playerNumber != line(beginIndex + y)) {
          parentGameFieldActor ! LineWinnerMessage(-1)        // Send message back
        }
      }
      playerNumber
    case _ => log.warning("Received unknown message")
  }
}
