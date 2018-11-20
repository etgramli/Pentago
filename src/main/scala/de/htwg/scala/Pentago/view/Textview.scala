package de.htwg.scala.Pentago.view

import scala.util.Try

class Textview {

  val playerSystem:PlayerSystem = new PlayerSystem()

  def play(): Unit ={
    while(true){
      
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
}

/**
  * Read Coordinates from console and test if the user has made the correct input with try-monads
  */
class LineReader {

  val coord:Coordinates = new Coordinates

  println("Please insert a number from 1 to 6 for x: ")
  while(coord.getY < 0 || coord.getY >= 6){
    val line = scala.io.StdIn.readLine()
    Try(coord.setX(line.toInt-1)).getOrElse(coord.setX(-2))
    if(coord.getX < 0 ||coord.getX >= 6) println("Please insert a correct number from 1 to 6! Try again: ")
  }

  println("Please insert a number from 1 to 6 for y: ")
  while(coord.getY < 0 || coord.getY >= 6){
    val line = scala.io.StdIn.readLine()
    Try(coord.setY(line.toInt-1)).getOrElse(coord.setY(-2))
    if(coord.getY < 0 || coord.getY >= 6) println("Please insert a correct number from 1 to 6! Try again: ")
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

