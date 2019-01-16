package de.htwg.scala.Pentago.view

import java.awt.Color

import de.htwg.scala.Pentago.controller.Controller
import de.htwg.scala.Pentago.model.Player
import de.htwg.scala.Pentago.view.observer.Observer
import javax.swing.{Icon, JOptionPane, UIManager}

import scala.swing.Swing.EmptyIcon
import scala.swing._
import scala.swing.event.ButtonClicked

class GUI(controller: Controller) extends MainFrame with Observer[Controller] {
  title = "Pentago"
  preferredSize = new Dimension(400, 400)
  var counter = 0
  val noWin = true

  def play() : Unit = {
        drawMap()
        popupNames()
        addReactions()
  }

  def testWin() : Unit = {
    val set = controller.testWin()
    if(set.size > 1){
      System.out.println(set(0) + " " + set(1))
      popupWinner("Unentschieden!")
    } else if(set.size == 1) {
      System.out.println(set(0))
      popupWinner("Der Gewinner ist: " + controller.currentPlayer.name)
    }
  }

  def popupWinner(message:String) : Unit = {
    Dialog.showMessage(contents.head, message, "Winner:")
  }

  def popupNames() : Unit ={
    val name1 = Dialog.showInput(contents.head, "Choose the Name of Player 1", "Header", Dialog.Message.Question, null, Nil, "")
    val name2 = Dialog.showInput(contents.head, "Choose the Name of Player 2", "Header", Dialog.Message.Question, null, Nil, "")

    if(name1.isDefined) controller.players(0) = new Player(1,name1.get)
    if(name2.isDefined) controller.players(1) = new Player(2,name2.get)
    controller.currentPlayer = controller.players(0)

    drawMap()
  }

  def popupRotation() : Unit = {
    val stringsFields = Array("1", "2", "3", "4")
    val stringsDirection = Array("Left", "Right")
    val re = Dialog.showConfirmation(contents.head, "Rotate a Field?", "Rotate Field", Dialog.Options.YesNo, Dialog.Message.Question, null)
    if(re.eq(Dialog.Result.Yes)) {
      val number = showOptions(contents.head, "Which one? (From top left to bot right)", "Rotate Field",  Dialog.Message.Question, null, stringsFields, 0)
      val direction = showOptions(contents.head, "In which direction?", "Rotate Field", Dialog.Message.Question, null, stringsDirection, 0)
      if(direction.get == 0){
        controller.rotate(number.get, 'l')
      } else if(direction.get == 1) {
        controller.rotate(number.get, 'r')
      }
    }

    controller.switchPlayer()
    counter += 1

    if(noWin) {
      drawMap()
    }
  }

  def showOptions[A <: Enumeration](
                                     parent: Component = null,
                                     message: Any,
                                     title: String = UIManager.getString("OptionPane.titleText"),
                                     messageType: Dialog.Message.Value = Dialog.Message.Question,
                                     icon: Icon = EmptyIcon,
                                     entries: Array[String],
                                     initial: Int): Option[Int] = {
    val r = JOptionPane.showOptionDialog(
      if (parent == null) null else parent.peer,  message, title, 0,
      messageType.id, Swing.wrapIcon(icon),
      entries.asInstanceOf[Array[AnyRef]], initial)
    if (r < 0) None else Some(r)
  }



  def addReactions() : Unit = {
    reactions += {
      case ButtonClicked(b) =>
        if(noWin) {
          if (controller.currentPlayer.number == 1) {
            b.background = Color.magenta
          } else if (controller.currentPlayer.number == 2) {
            b.background = Color.blue
          }
          val coords = b.name.split(";")
          controller.placeOrb(coords(0).toInt, coords(1).toInt, controller.currentPlayer.number)
          testWin()
          if(noWin) {
            popupRotation()
            testWin()
          }
          b.enabled = false
        }
    }
  }

  //----------- Draw Map -------------//

  def drawPlayer : BoxPanel = new BoxPanel(Orientation.Horizontal){
    contents += drawPlayerLabel(controller.players(0).name, "playerX")
    contents += new Label ("vs")
    contents += drawPlayerLabel(controller.players(1).name, "playerY")
  }

  def drawRound : BoxPanel = new BoxPanel(Orientation.Horizontal){
    contents += drawPlayerLabel("Round " + counter, "round")
    contents += new Label ("----")
    contents += drawPlayerLabel(controller.currentPlayer.name, "actualPlayer")
  }

  def drawPlayerLabel(name:String, id:String) : Label = {
    new Label(name){
      maximumSize = new Dimension(Short.MaxValue, Short.MaxValue)
      name = id
    }
  }

  def drawInternMap : BoxPanel = new BoxPanel(Orientation.Vertical){
    for(x <- 0 until 6){
      contents += drawLine(x)
    }
  }

  def drawLine(id:Int) : BoxPanel =  new BoxPanel(Orientation.Horizontal){
    for(x <- 0 until 6) {
      contents += drawSingleDot(id.toString + ";" + x.toString)
    }
  }

  def drawSingleDot(id:String) : Button = {
    val coords = id.split(";")
    val button = new Button(){
      maximumSize = new Dimension(Short.MaxValue, Short.MaxValue)
      name = id
      if(controller.orbAt(coords(0).toInt, coords(1).toInt) == 1){
          background = Color.magenta
      } else if(controller.orbAt(coords(0).toInt, coords(1).toInt) == 2){
        background = Color.blue
      }
    }

    listenTo(button)
    button
  }

  def drawMap() : Unit = {
    contents = new BoxPanel(Orientation.Vertical){
      contents += drawPlayer
      contents += drawRound
      contents += drawInternMap
    }
  }

  override def receiveUpdate(): Unit = {
    // ToDo: Update game field view
  }
}


