package view

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage

class WarningWindow {

  private val warningStage = new Stage()

  private val buttonOk = new Button("Заполнены не все поля")

  private val layoutXForScene = 220
  private val layoutYForScene = 100

  warningStage.setScene(new Scene(buttonOk, layoutXForScene, layoutYForScene))
  warningStage.setResizable(false)

  def warningButtonOK() = {
    warningStage.show()

    //fixme лямбды
    buttonOk.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        warningStage.close()
      }
    })
  }
}
