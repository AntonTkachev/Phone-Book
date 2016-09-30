package Interface

import java.io.{File, FileWriter}
import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout.{FlowPane, HBox}
import javafx.stage.Stage

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}

class MainWindow extends Application {

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")
    val delete = new Button("Delete All")
    val save = new Button("Save")

    val clearWindow = new ClearWindow()
    val clear = clearWindow.clear

    val firstName = new TextField("Name")
    val number = new TextField("Number")

    delete.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        firstName.setText("")
        number.setText("")
      }
    })

    save.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        val pw = new FileWriter(new File("test.csv"), true)
        pw.write(s"${firstName.getText}; ${number.getText};")
        pw.close()
      }
    })

    clearWindow.openClearWindow(clear)

    val close = new MenuItem("Close")
    val showContacts = new MenuItem("Show contacts")

    close.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    close.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        primaryStage.close()
      }
    })

    showContacts.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN))
    showContacts.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        val lay = new FlowPane()

        val stageWithContacts = new Stage()
        stageWithContacts.setTitle("Все контакты")
        stageWithContacts.setScene(new Scene(lay, 300, 250))
        stageWithContacts.show()

      }
    })

    val menuButton = new MenuButton("Edit", null, close, showContacts)
    val hbox = new HBox(menuButton)

    val root = new FlowPane()
    save.setMaxWidth(Double.MaxValue)
    delete.setMaxWidth(Double.MaxValue)
    clear.setMaxWidth(Double.MaxValue)
    root.setOrientation(Orientation.VERTICAL)
    root.getChildren.add(firstName)
    root.getChildren.add(number)
    root.getChildren.add(delete)
    root.getChildren.add(save)
    root.getChildren.add(clear)
    root.getChildren.add(0, hbox)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show
  }

}
