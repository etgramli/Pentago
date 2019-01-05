package de.htwg.scala.Pentago.controller

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import de.htwg.scala.Pentago.controller.actors.{GameFieldTestActor, TestGameFieldMessage}
import de.htwg.scala.Pentago.model.{GameField, Player, Tile}

import scala.concurrent.Await
import scala.concurrent.duration._


class Controller(var gameField: GameField, val players: Array[Player]) {

  val actorSystem = ActorSystem("Controller-System")
  implicit val timeout: Timeout = Timeout(5 second)
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
    gameField.getGameFiled()
  }

  // Test win condition (-1: Nobody won yet, else: playerNumber)
  def testWin(): Set[Int] = {
    val actorVertical = actorSystem.actorOf(GameFieldTestActor.props(), "FieldActorVertical")
    val actorHorizontal = actorSystem.actorOf(GameFieldTestActor.props(), "FieldActorHorizontal")
    val futureVertical = actorVertical ? TestGameFieldMessage(gameField.rotateGameFieldLeft())
    val futureHorizontal = actorHorizontal ? TestGameFieldMessage(gameField)

    val winnersVertical = Await.result(futureVertical, 3 seconds)
    val winnersHorizontal = Await.result(futureHorizontal, 3 seconds)
    winnersHorizontal.asInstanceOf[Set[Int]] union winnersVertical.asInstanceOf[Set[Int]]
  }

  def testWin(gameField: GameField): Set[Int] = {
    var winners = Set[Int]()
    var playerNumber = -1
    for (x <- 0 until gameField.size) {
      for (y <- 0 until 2) {
        playerNumber = testHorizontalRow(x, y, gameField)
        if (playerNumber != -1) {
          winners += playerNumber
        }
        if (x < 2) {
          playerNumber = testDiagonalRow(x, y, gameField)
          if (playerNumber != -1) {
            winners += playerNumber
          }
        }
      }
    }
    winners
  }

  def testHorizontalRow(xStart: Int, yStart: Int, gameField: GameField): Int = {
    val playerNumber = gameField.orbAt(xStart, yStart)
    if (playerNumber == -1)
      return playerNumber
    for (y <- yStart until yStart + 5) {
      if (yStart + y >= gameField.size || playerNumber != gameField.orbAt(xStart, yStart + y)) {
        return -1
      }
    }
    playerNumber
  }

  def testDiagonalRow(xStart: Int, yStart: Int, gameField: GameField): Int = {
    val playerNumber = gameField.orbAt(xStart, yStart)
    if (playerNumber == -1) {
      return playerNumber
    }
    for (x <- 0 until 5) {
      if (xStart + x >= gameField.size || yStart + x >= gameField.size || gameField.orbAt(xStart + x, yStart + x) != playerNumber) {
        return -1
      }
    }
    playerNumber
  }
}
