import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.{MenuButton, MenuItem, TextField}
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout.HBox
import javafx.stage._

class MenuButtonExperiments extends Application {
  def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Новое Меню")

    val close = new MenuItem("Close")
    val number = new TextField("Number")

    val menuButton = new MenuButton("Hello", null, close)

    val hbox = new HBox(menuButton)

    close.setAccelerator(new KeyCodeCombination(KeyCode.X,KeyCombination.CONTROL_DOWN))
    close.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        primaryStage.close()
      }
    })

    val scene = new Scene(hbox, 200, 100)
    primaryStage.setScene(scene)
    primaryStage.getTitle
    primaryStage.show()
  }
}

object MenuButtonExperiments {
  def main(args: Array[String]) {
    Application.launch(classOf[MenuButtonExperiments], args: _*)
  }
}
