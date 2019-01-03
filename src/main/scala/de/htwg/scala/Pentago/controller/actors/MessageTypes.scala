package de.htwg.scala.Pentago.controller.actors

import de.htwg.scala.Pentago.model.GameField


final case class TestGameFieldMessage(gf: GameField)
final case class GameFieldWinners(playerNumbers: Set[Int])

final case class Line(line: Array[Int])
final case class LineWinner(playerNumber: Int)
