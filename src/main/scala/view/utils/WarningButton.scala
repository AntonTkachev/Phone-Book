package view.utils

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage

import view.helpers.LambdaHelper._

object WarningButton {

  val warningStageWithOK = new Stage()
  val buttonOk = new Button()

  warningStageWithOK.setScene(new Scene(buttonOk, 220, 100))
  warningStageWithOK.setResizable(false)

  def ok(message: String) = {
    buttonOk.setText(message)
    warningStageWithOK.show()

    buttonOk.setOnAction((e: ActionEvent) =>
      warningStageWithOK.close()
    )
  }
}
