package de.htwg.scala.Pentago.view

import de.htwg.scala.Pentago.controller.Controller

import scala.util.Try

class Textview {

  val playerSystem:PlayerSystem = new PlayerSystem()

  def play(controller: Controller): Unit ={
    //ToDo: Welcomeword
    drawMap(controller)
    while(true){
      displayPlayer()
      //ToDo: Ask if rotate and if then rotate
      val coord:Coordinates = lineReader()
      setStone(coord, getCurrentPlayer,controller)
      drawMap(controller)
      changePlayer()
    }
  }

  def changePlayer(): Unit = {
    playerSystem.changeState()
  }

  def displayPlayer(): Unit = {
    playerSystem.displayState()
  }

  def getCurrentPlayer : PlayerState = {
    playerSystem.currentPlayer
  }

  def drawMap(controller:Controller): Unit = {
    controller
  }

  def setStone(coordinates: Coordinates, playerState: PlayerState, controller:Controller): Unit  = {
    controller.placeOrb(coordinates.getX, coordinates.getY,playerState.getPlayerNumber)
  }

  def lineReader():Coordinates = {

    val coord: Coordinates = new Coordinates

    println("Please insert a number from 1 to 6 for x: ")
    while (coord.getY < 0 || coord.getY >= 6) {
      val line = scala.io.StdIn.readLine()
      Try(coord.setX(line.toInt - 1)).getOrElse(coord.setX(-2))
      if (coord.getX < 0 || coord.getX >= 6) println("Please insert a correct number from 1 to 6! Try again: ")
    }

    println("Please insert a number from 1 to 6 for y: ")
    while (coord.getY < 0 || coord.getY >= 6) {
      val line = scala.io.StdIn.readLine()
      Try(coord.setY(line.toInt - 1)).getOrElse(coord.setY(-2))
      if (coord.getY < 0 || coord.getY >= 6) println("Please insert a correct number from 1 to 6! Try again: ")
    }

    coord
  }
}

class Coordinates {
  var x:Int = -1
  var y:Int = -1

  def getX:Int = x
  def getY:Int = y
  def setX(x:Int):Unit = this.x = x
  def setY(y:Int):Unit = this.y = y
}

