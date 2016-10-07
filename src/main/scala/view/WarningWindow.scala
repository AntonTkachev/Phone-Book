package Interface

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage

class WarningWindow {

  val warningStage = new Stage()

  val buttonOk = new Button("Заполнены не все поля")

  val layoutXForScene = 220
  val layoutYForScene = 100

  def warningButtonOK() ={
    warningStage.setScene(new Scene(buttonOk, layoutXForScene, layoutYForScene))
    warningStage.setResizable(false)
    warningStage.show()

    buttonOk.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        warningStage.close()
      }
    })
  }
}
