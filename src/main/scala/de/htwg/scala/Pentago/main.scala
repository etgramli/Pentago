import de.htwg.scala.Pentago.controller.Controller
import de.htwg.scala.Pentago.view.Textview

object main extends App {
  override def main(args: Array[String]): Unit = {

    val controller = new Controller("Player1", "Player2")
    val textview = new Textview()
    textview.play(controller)
  }
}