package de.htwg.scala.Pentago.controller

import java.util.UUID

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import de.htwg.scala.Pentago.controller.actors.{GameFieldTestActor, GameFieldWinnersMessage, TestGameFieldMessage}
import de.htwg.scala.Pentago.model.{GameField, Player, Tile}

import scala.concurrent.Await
import scala.concurrent.duration._


class Controller(var gameField: GameField, val players: Array[Player]) {

  val actorSystem = ActorSystem("Controller-System")
  implicit val timeout: Timeout = Timeout(1 second)
  var currentPlayer = new Player(-1, "Dummy")

  def this(playerOneName: String, playerTwoName: String) {
    this(new GameField(), Array(new Player(0, playerOneName), new Player(1, playerTwoName)))
    this.currentPlayer = players(0)
  }


  def switchPlayer(): Unit = {
    val currentPlayerIndex = players.indexOf(currentPlayer)
    val nextPlayerIndex = (currentPlayerIndex + 1) % players.length
    currentPlayer = players(nextPlayerIndex)
  }

  def getCurrentPlayer: Player = {
    currentPlayer
  }

  def getCurrentPlayerIndex: Int = {
    players.indexOf(currentPlayer)
  }

  // 'l' links, 'r' rechts
  def rotate(tileNumber: Int, direction: Char): Unit = {
    this.gameField = gameField.rotate(tileNumber, direction)
  }

  def placeOrb(xCoord: Int, yCoord: Int, playerNumber: Int): Boolean = {
    if (gameField.orbAt(xCoord, yCoord) != -1) {
      false
    } else {
      this.gameField = gameField.placeOrb(xCoord, yCoord, playerNumber)
      true
    }
  }

  def getAllTiles: Array[Array[Tile]] = {
    gameField.tiles.clone()
  }

  def getGameFiled: Array[Array[Int]] = {
    gameField.getGameFiled
  }

  // Test win condition (-1: Nobody won yet, else: playerNumber)
  def testWin(): Set[Int] = {
    val actorVertical = actorSystem.actorOf(GameFieldTestActor.props(), "FieldActorVertical"+UUID.randomUUID())
    val actorHorizontal = actorSystem.actorOf(GameFieldTestActor.props(), "FieldActorHorizontal"+UUID.randomUUID())
    val futureHorizontal = actorHorizontal ? TestGameFieldMessage(gameField)
    val rotated = gameField.rotateGameFieldLeft()
    val futureVertical = actorVertical ? TestGameFieldMessage(rotated)

    val winnersVertical = Await.result(futureVertical, 1 seconds)
    val winnersHorizontal = Await.result(futureHorizontal, 1 seconds)
    val winnersVerticalSet = winnersVertical.asInstanceOf[GameFieldWinnersMessage].playerNumbers
    val winnersHorizontalSet = winnersHorizontal.asInstanceOf[GameFieldWinnersMessage].playerNumbers

    winnersVerticalSet union winnersHorizontalSet
  }
}
