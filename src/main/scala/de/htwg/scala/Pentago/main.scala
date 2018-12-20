import de.htwg.scala.Pentago.view.{PlayerOne, Textview}

object HelloWorld {
  def main(args: Array[String]): Unit = {
    createField()
  }

  def createField(): Unit = {
    //Todo: Create Gamefield over controller and give it to view
    //Todo: Create while-Loop to play()

    val textview = new Textview()
    while(true){
      textview.displayPlayer()
      if(textview.getCurrentPlayer.isInstanceOf[PlayerOne]){
        Thread.sleep(5000)
        textview.changePlayer()
      } else {
        Thread.sleep(5000)
        textview.changePlayer()
      }
    }
  }
}