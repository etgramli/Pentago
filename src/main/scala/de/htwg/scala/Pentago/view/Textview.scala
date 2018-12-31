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

  def printList(args: TraversableOnce[_]): Unit = {
    args.foreach(print)
  }

  def drawMap(controller:Controller): Unit = {
    val array = adjustArray(controller.getGameFiled())
    for(x <- array){
      printList(x)
      println()
    }
  }

  def adjustArray(arrays:Array[Array[Int]]): Array[Array[String]] = {
    val allArrays = arrays.map(x => adjustSingleArray(x))
    val line = Array[String](" ", "_", " ", "_", " ", "_", " ", "_", " ", "_", " ", " ", "_", " ", "_", " ", "_", " ", "_", " ")

    val finalLine = Array[Array[String]](line)

    Array[Array[String]]() ++ finalLine ++ allArrays ++ finalLine
  }

  /**
    * Changes the given int array into string array, with | in the middle and on the edges
    * @param array to change
    * @return changed array
    */
  def adjustSingleArray(array:Array[Int]): Array[String] = {
    val arr = array.map(x => x.toString)
    for(x <- arr){
      if(x == "-1"){
        array(array.indexOf(x)) = "0"
      }
    }

    Array[String]("|") ++ arr.take(3) ++ Array[String]("|") ++ arr.drop(3) ++ Array[String]("|")
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

