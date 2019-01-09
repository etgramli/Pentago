import akka.actor.ActorSystem
import de.htwg.scala.Pentago.controller.Controller
import de.htwg.scala.Pentago.view.{GUI, Textview}

object Main extends App {
  var system = ActorSystem("PentagoSystem")
  //val myActor = system.actorOf(Props[MyActor], name = "PentagoActor")

  override def main(args: Array[String]): Unit = {

    val controller = new Controller("Player1", "Player2")
    val textview = new Textview()
    val gui = new GUI(controller)
    gui.visible = true
    gui.play()
    //textview.play(controller)

    println("End of Programm")
  }
}