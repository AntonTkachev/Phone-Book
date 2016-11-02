package view.helpers

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.stage.Stage

trait UIHelper {
  val pane = new Pane()

  val warningStage = new Stage()

  val buttonNo = new Button("No")
  val buttonYes = new Button("Yes")

  pane.getChildren.add(buttonYes)
  pane.getChildren.add(buttonNo)

  val layoutXForButton = 115
  val layoutYForButton = 70
  val layoutXForScene = 220
  val layoutYForScene = 60

  buttonNo.setLayoutX(layoutXForButton)
  buttonNo.setMinSize(layoutXForButton, layoutYForButton)
  buttonYes.setMinSize(layoutXForButton, layoutYForButton)

  warningStage.setTitle("Clear all contact?")
  warningStage.setScene(new Scene(pane, layoutXForScene, layoutYForScene))
  warningStage.setResizable(false)
}
