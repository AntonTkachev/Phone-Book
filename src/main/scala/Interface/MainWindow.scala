package Interface

import java.util.Scanner
import java.io.File
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
  val pane = new FlowPane()

  val stageWithContacts = new Stage()

  val buttonDelete = new Button("Delete All")
  val buttonNewContact = new Button("Add new contact")

  val textFieldName = new TextField("Name")
  val textFieldNumber = new TextField("Number")

  val menuItemClose = new MenuItem("Close")
  val menuItemShowContacts = new MenuItem("Show contacts")

  val menuButtonEdit = new MenuButton("Edit", null, menuItemClose, menuItemShowContacts)

  val scanner = new Scanner(new File(Utils.pathToCsvFile))

  val listView: ListView[String] = new ListView()

  val clearWindow = new ClearWindow()
  val clearButton = clearWindow.clearWindowButton

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    buttonDelete.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        textFieldName.setText("")
        textFieldNumber.setText("")
      }
    })

    buttonNewContact.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        Utils.writeToDB()
      }
    })

    clearWindow.openClearWindowWithButton(clearButton)

    menuItemClose.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    menuItemClose.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        primaryStage.close()
      }
    })

    menuItemShowContacts.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN))
    menuItemShowContacts.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        scanner.useDelimiter(",")
        while (scanner.hasNext()) {
          val allContactsFromBD = scanner.next()
          val contact = allContactsFromBD.split(';')
          for (num <- contact.indices) {
            listView.getItems.add(contact(num))
          }
        }
        scanner.close()

        stageWithContacts.setTitle("Все контакты")
        stageWithContacts.setScene(new Scene(new HBox(listView), 300, 250))
        stageWithContacts.show()
      }
    })

    buttonNewContact.setMaxWidth(Double.MaxValue)
    buttonDelete.setMaxWidth(Double.MaxValue)
    clearButton.setMaxWidth(Double.MaxValue)
    pane.setOrientation(Orientation.VERTICAL)
    pane.getChildren.add(textFieldName)
    pane.getChildren.add(textFieldNumber)
    pane.getChildren.add(buttonDelete)
    pane.getChildren.add(buttonNewContact)
    pane.getChildren.add(clearButton)
    pane.getChildren.add(0, new HBox(menuButtonEdit))
    primaryStage.setScene(new Scene(pane, 300, 250))
    primaryStage.show()
  }

}
