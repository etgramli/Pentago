package de.htwg.scala.Pentago.view

class PlayerSystem {

  var currentPlayer:PlayerState = new PlayerOne(this)
  var previousState:PlayerState = new PlayerTwo(this)
  var playerOne:PlayerOne = new PlayerOne(this)
  var playerTwo:PlayerTwo = new PlayerTwo(this)

  def changeState(): Unit ={
    currentPlayer.changePlayer()
  }

  def displayState(): Unit ={
    currentPlayer.displayPlayer()
  }
}

trait PlayerState{
  def changePlayer()
  def displayPlayer()
  def getPlayerNumber:Int
}

class PlayerOne(system: PlayerSystem) extends PlayerState {
  override def changePlayer(): Unit = {
    system.previousState = this
    system.currentPlayer = system.playerTwo
  }

  override def displayPlayer(): Unit = {
    println("Spieler 1 am Zug")
  }

  override def getPlayerNumber: Int = {
    1
  }
}

class PlayerTwo(system: PlayerSystem) extends PlayerState {
  override def changePlayer(): Unit = {
    system.previousState = this
    system.currentPlayer = system.playerOne
  }

  override def displayPlayer(): Unit = {
    println("Spieler 2 am Zug")
  }

  override def getPlayerNumber: Int = {
    2
  }
}