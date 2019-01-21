import akka.actor.ActorSystem
import com.google.inject.{AbstractModule, Guice}
import de.htwg.scala.Pentago.controller.{Controller, ControllerInterface}
import de.htwg.scala.Pentago.view.{GUI, Textview}
import net.codingwell.scalaguice.ScalaModule

object Main extends App {
  var system = ActorSystem("PentagoSystem")
  //val myActor = system.actorOf(Props[MyActor], name = "PentagoActor")

  override def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new PentagoModule())

    val textUIService = injector.getInstance(classOf[Textview])
    val guiService = injector.getInstance(classOf[GUI])

    textUIService.getController.addObserver(textUIService)
    textUIService.getController.addObserver(guiService)

    println("End of Programm")
  }
}

class PentagoModule extends AbstractModule with ScalaModule {
  def configure():Unit = {
    bind[ControllerInterface].to[Controller]
  }
}