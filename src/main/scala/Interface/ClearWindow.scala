package Interface

import java.io.{File, FileWriter}
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout._
import javafx.stage.Stage

class ClearWindow {
  val clear = new Button("Clear BD")

  def openClearWindow(value: Button) = {
    value.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        val no = new Button("No")
        val yes = new Button("Yes")
        val pane = new Pane()
        no.setLayoutX(115)
        no.setMinSize(115,70)
        yes.setMinSize(115,70)

        pane.getChildren.add(yes)
        pane.getChildren.add(no)
        val secSt = new Stage()
        secSt.setTitle("Clear BD???")
        secSt.setScene(new Scene(pane, 220, 60))
        secSt.setResizable(false)
        secSt.show

        yes.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            new FileWriter(new File("test.csv"), false)
            secSt.close()
          }
        })
        no.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            secSt.close()
          }
        })
      }
    })
  }
}