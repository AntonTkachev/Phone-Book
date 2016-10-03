package Interface

import java.io.{File, FileWriter}
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout._
import javafx.stage.Stage

class ClearWindow {
  val warningStage = new Stage()

  val buttonNo = new Button("No")
  val buttonYes = new Button("Yes")

  val layoutXForButton = 115
  val layoutYForButton = 70

  val layoutXForScene = 220
  val layoutYForScene = 60

  def openClearWindowWithButton(value: Button) = {
    value.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        val pane = new Pane()

        buttonNo.setLayoutX(layoutXForButton)
        buttonNo.setMinSize(layoutXForButton, layoutYForButton)
        buttonYes.setMinSize(layoutXForButton, layoutYForButton)

        pane.getChildren.add(buttonYes)
        pane.getChildren.add(buttonNo)

        warningStage.setTitle("Clear BD???")
        warningStage.setScene(new Scene(pane, layoutXForScene, layoutYForScene))
        warningStage.setResizable(false)
        warningStage.show()

        buttonYes.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            Utils.clearDB()
            warningStage.close()
          }
        })

        buttonNo.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            warningStage.close()
          }
        })
      }
    })
  }
}