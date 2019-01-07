package de.htwg.scala.Pentago.view

import de.htwg.scala.Pentago.controller.Controller
import de.htwg.scala.Pentago.model.Player

import scala.util.Try

class Textview {

  var counter = 1

  /**
    * The "main" function to let the textview run
    * @param controller from which the functions are from
    */
  def play(controller: Controller): Unit ={
    lineReaderHello(controller)
    while(true){
      println("----- Round " + counter +" Start -----")
      println(controller.currentPlayer.name + "(" + controller.currentPlayer.number + "):")
      drawMap(controller)
      lineReaderCoordinates(controller)
      lineReaderRotateField(controller)
      drawMap(controller)
      counter+=1
      controller.switchPlayer()
    }
  }

  /**
    * Prints a List of Variables
    * @param args the list which should be printed
    */
  def printList(args: TraversableOnce[_]): Unit = {
    args.foreach(print)
  }

  /**
    * Draws the map
    * @param controller from which the array comes from
    */
  def drawMap(controller:Controller): Unit = {
    val array = adjustArray(controller.getGameFiled())
    for(x <- array){
      printList(x)
      println()
    }
  }

  /**
    * Changes the array of arrays to a acceptable looking text map
    * @param arrays to change
    * @return changed array of arrays
    */
  def adjustArray(arrays:Array[Array[Int]]): Array[Array[String]] = {
    val allArrays = arrays.map(x => adjustSingleArray(x))
    val line = Array[String](" ", " ", "-", " ", "-", " ", "-", " ", " ", " ", "-", " ", "-", " ", "-")

    val finalLine = Array[Array[String]](line)

    Array[Array[String]]() ++ finalLine ++ allArrays.take(3) ++ finalLine ++ allArrays.drop(3) ++ finalLine
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
        arr(arr.indexOf(x)) = "0 "
      } else {
        arr(arr.indexOf(x)) = x + ' '
      }
    }

    Array[String]("| ") ++ arr.take(3) ++ Array[String]("| ") ++ arr.drop(3) ++ Array[String]("|")
  }

  def lineReaderRotateField(controller: Controller): Unit ={
    print("Do you want to rotate on of the four fields? (y,n): ")
    val answer = scala.io.StdIn.readChar()
    if(answer == 'y'){
      print("Which one? (1,2,3,4 from top left to bottom right): ")
      var answer2 = scala.io.StdIn.readChar()
      while(answer2 != '1' && answer2 != '2' && answer2 != '3' && answer2 != '4') {
        if (answer2 != '1' && answer2 != '2' && answer2 != '3' && answer2 != '4') {
          print("Please decide for 1 to 4: ")
          answer2 = scala.io.StdIn.readChar()
        }
      }
      if (answer2 == '1' || answer2 == '2' || answer2 == '3' || answer2 == '4'){
          print("In which direction? (l, r): ")
          var answer3 = scala.io.StdIn.readChar()
          while(answer3 != 'l' && answer3 != 'r'){
            if(answer3 != 'l' && answer3 != 'r'){
              print("Please insert l for left and r for right: ")
              answer3 = scala.io.StdIn.readChar()
            } else {
              controller.rotate(answer2.toInt - 1, answer3)
            }
          }
        }
    } else if(answer != 'n'){
      println("Please insert y for yes or n for no")
    }
  }

  def lineReaderHello(controller:Controller): Unit = {
    var player1Name = "player 1"
    var player2Name = "player 2"

    println("Welcome to Pentago! Please insert playernames:")
    print("Player 1: ")


    player1Name = scala.io.StdIn.readLine()

    print("Player 2: ")
    player2Name = scala.io.StdIn.readLine()

    controller.players(0) = new Player(1,player1Name)
    controller.players(1) = new Player(2,player2Name)
    controller.currentPlayer = controller.players(0)

    println("Have Fun!")
  }

  /**
    * Linereader to read the coordinates
    * @return the coordinates
    */
  def lineReaderCoordinates(controller: Controller):Unit = {

    val coord: Coordinates = new Coordinates

    println("Please insert a number from 1 to 6 for x: ")
    while (coord.getX < 0 || coord.getX >= 6) {
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

    if (!controller.placeOrb(coord.getX, coord.getY,controller.currentPlayer.number)){
      println("This Place is already occupied, please choose another one")
      coord.setX(-2)
      coord.setY(-2)
      lineReaderCoordinates(controller)
    }
  }
}

/**
  * Class for the Coordinates to easier manipulate them
  */
class Coordinates {
  var x:Int = -1
  var y:Int = -1

  def getX:Int = x
  def getY:Int = y
  def setX(x:Int):Unit = this.x = x
  def setY(y:Int):Unit = this.y = y
}

