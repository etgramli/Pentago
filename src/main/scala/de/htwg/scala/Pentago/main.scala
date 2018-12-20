import de.htwg.scala.Pentago.controller.Controller
import de.htwg.scala.Pentago.view.Textview

object main extends App {
  override def main(args: Array[String]): Unit = {

    val controller = new Controller("Spieler 1", "Spieler 2")
    val view = new Textview()
    view.play(controller)
  }
}