package view

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout._
import javafx.stage.Stage

import LambdaHelper._

class ClearAllContactWindow {
  private val warningStage = new Stage()

  private val buttonNo = new Button("No")
  private val buttonYes = new Button("Yes")

  private val layoutXForButton = 115
  private val layoutYForButton = 70

  private val layoutXForScene = 220
  private val layoutYForScene = 60

  private val pane = new Pane()

  buttonNo.setLayoutX(layoutXForButton)
  buttonNo.setMinSize(layoutXForButton, layoutYForButton)
  buttonYes.setMinSize(layoutXForButton, layoutYForButton)

  pane.getChildren.add(buttonYes)
  pane.getChildren.add(buttonNo)

  warningStage.setTitle("Clear all contact?")
  warningStage.setScene(new Scene(pane, layoutXForScene, layoutYForScene))
  warningStage.setResizable(false)

  def clearAllContact(value: Button) = {
    value.setOnAction((e: ActionEvent) => {

      warningStage.show()

      buttonYes.setOnAction((e: ActionEvent) => {
        Utils.clearDB()
        Constants.listView.getItems.clear()
        warningStage.close()
      })

      buttonNo.setOnAction((e: ActionEvent) =>
        warningStage.close()
      )
    })
  }
}