package Interface

import java.io.{File, FileWriter}
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.FlowPane
import javafx.stage.Stage

class ClearWindow {
  val clear = new Button("Clear BD")

  def openClearWindow(value: Button) = {
    value.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        val no = new Button("No")
        val yes = new Button("Yes")
        val lay = new FlowPane()
        lay.getChildren.add(no)
        lay.getChildren.add(yes)

        val secSt = new Stage()
        secSt.setTitle("Точно хотите очистит БД?")
        secSt.setScene(new Scene(lay, 200, 100))
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