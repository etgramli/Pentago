package de.htwg.scala.Pentago.controller

import java.util.UUID

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.{Inject,Singleton}
import de.htwg.scala.Pentago.controller.actors.{GameFieldTestActor, GameFieldWinnersMessage, TestGameFieldMessage}
import de.htwg.scala.Pentago.model.{GameField, Player, Tile}
import de.htwg.scala.Pentago.view.observer.Subject

import scala.concurrent.Await
import scala.concurrent.duration._

trait ControllerInterface extends Subject[ControllerInterface] {
  def switchPlayer()
  def getCurrentPlayer: Player
  def getCurrentPlayerIndex: Int
  def rotate(tileNumber: Int, direction: Char)
  def placeOrb(xCoord: Int, yCoord: Int, playerNumber: Int): Boolean
  def orbAt(xCoord: Int, yCoord: Int) : Int
  def getAllTiles: Array[Array[Tile]]
  def getGameFiled: Array[Array[Int]]
  def testWin(): Set[Int]
  def getPlayers: Array[Player]
}

@Singleton
class Controller (var gameField: GameField, val players: Array[Player]) extends ControllerInterface {

  val actorSystem = ActorSystem("Controller-System")
  implicit val timeout: Timeout = Timeout(1 second)
  var currentPlayer = new Player(-1, "Dummy")

  @Inject()
  def this(playerOneName: String, playerTwoName: String) {
    this(new GameField(), Array(new Player(0, playerOneName), new Player(1, playerTwoName)))
    this.currentPlayer = players(0)
  }


  override def switchPlayer(): Unit = {
    val currentPlayerIndex = players.indexOf(currentPlayer)
    val nextPlayerIndex = (currentPlayerIndex + 1) % players.length
    currentPlayer = players(nextPlayerIndex)

    notifyObservers()
  }

  override def getPlayers(): Array[Player] = players

  override def getCurrentPlayer: Player = {
    currentPlayer
  }

  override def getCurrentPlayerIndex: Int = {
    players.indexOf(currentPlayer)
  }

  // 'l' links, 'r' rechts
  override def rotate(tileNumber: Int, direction: Char): Unit = {
    this.gameField = gameField.rotate(tileNumber, direction)

    notifyObservers()
  }

  override def placeOrb(xCoord: Int, yCoord: Int, playerNumber: Int): Boolean = {
    if (gameField.orbAt(xCoord, yCoord) != -1) {
      false
    } else {
      this.gameField = gameField.placeOrb(xCoord, yCoord, playerNumber)
      notifyObservers()
      true
    }
  }

  override def orbAt(xCoord: Int, yCoord: Int) : Int = {
    gameField.orbAt(xCoord, yCoord)
  }

  override def getAllTiles: Array[Array[Tile]] = {
    gameField.tiles.clone()
  }

  override def getGameFiled: Array[Array[Int]] = {
    gameField.getGameFiled
  }

  // Test win condition (-1: Nobody won yet, else: playerNumber)
  override def testWin(): Set[Int] = {
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