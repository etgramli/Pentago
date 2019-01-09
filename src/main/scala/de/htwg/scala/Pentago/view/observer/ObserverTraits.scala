package de.htwg.scala.Pentago.view.observer

trait Observer[Controller] {
  def receiveUpdate()
}

trait Subject[Controller] {
  this: Controller =>
  private var observers: List[Observer[Controller]] = Nil

  def addObserver(observer: Observer[Controller]): Unit = {
    observers = observer :: observers
  }

  def notifyObservers(): Unit = {
    observers.foreach(_.receiveUpdate())
  }
}
