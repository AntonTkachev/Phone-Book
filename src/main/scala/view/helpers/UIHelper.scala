package view.helpers

import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.stage.Stage

import view.secondaryWindows.UI._

trait UIHelper {
  val pane = new Pane()

  val warningStageWithOK = new Stage()

  val warningStage = new Stage()

  val buttonNo = new Button("No")
  val buttonYes = new Button("Yes")
  val buttonOk = new Button()



  val layoutXForButton = 115
  val layoutYForButton = 70
  val layoutXForScene = 220
  val layoutYForScene = 60


}
