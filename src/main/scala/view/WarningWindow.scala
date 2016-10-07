package view

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage

import LambdaHelper._

class WarningWindow {

  private val warningStage = new Stage()

  private val buttonOk = new Button("Заполнены не все поля")

  private val layoutXForScene = 220
  private val layoutYForScene = 100

  warningStage.setScene(new Scene(buttonOk, layoutXForScene, layoutYForScene))
  warningStage.setResizable(false)

  def warningButtonOK() = {
    warningStage.show()

    buttonOk.setOnAction((e: ActionEvent) =>
      warningStage.close()
    )
  }
}
