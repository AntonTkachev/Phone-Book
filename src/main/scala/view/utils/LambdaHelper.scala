package view.utils

import javafx.event.{ActionEvent, EventHandler}

object LambdaHelper {
  implicit def actionEventForHandle(event: (ActionEvent) => Unit) = new EventHandler[ActionEvent] {
    override def handle(dEvent: ActionEvent): Unit = event(dEvent)
  }
}
