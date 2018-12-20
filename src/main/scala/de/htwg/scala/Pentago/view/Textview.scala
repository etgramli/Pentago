package de.htwg.scala.Pentago.view

import de.htwg.scala.Pentago.controller.Controller

import scala.util.Try

class Textview {

  def play(controller: Controller): Unit ={
    //ToDo: Welcomeword
    drawMap(controller)
    while(true){
      //ToDo: Nach dem aktuellen Spieler fragen
      //ToDo: Ask if rotate and if then rotate
      val coord:Coordinates = lineReader()
      setStone(coord, controller)
      drawMap(controller)
    }
  }

  def drawMap(controller:Controller): Unit = {
    //Draw the damn Map
    val list = controller.getGameFiled()
    var i = 0
    while(i < 6){
      println(list[i])
      i += 1
    }
    println(list);
  }

  def setStone(coordinates: Coordinates, controller:Controller): Unit  = {
    controller.placeOrb(coordinates.getX, coordinates.getY,0)
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

