package de.htwg.scala.Pentago.controller.actors

import de.htwg.scala.Pentago.model.GameField


final case class TestGameFieldMessage(gf: GameField)
final case class GameFieldWinnersMessage(playerNumbers: Set[Int])

final case class LineMessage(line: Array[Int], yStart: Int)
final case class LineWinnerMessage(playerNumber: Int)
