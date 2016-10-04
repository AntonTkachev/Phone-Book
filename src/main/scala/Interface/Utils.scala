package Interface

import java.io.{File, FileWriter}
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage


object Utils {

  val pathToCsvFile = "test.csv"

  val warningStage = new Stage()

  val layoutXForScene = 220
  val layoutYForScene = 100

  def writeStrToCSV(str : String) ={
      val file = new FileWriter(new File(pathToCsvFile), true)
      file.write(str)
      file.close()
  }

  def writeToDB(name: String, number: String) = {
    val file = new FileWriter(new File(pathToCsvFile), true)
    if (name.nonEmpty && number.nonEmpty) {
      file.write(s"$name  $number;")
      file.close()
    }
    else {
      val buttonOk = new Button("Заполнены не все поля")
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

  def clearDB() = {
    val file = new FileWriter(new File(pathToCsvFile), false)
    file.close()
  }
}
