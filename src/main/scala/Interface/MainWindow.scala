package Interface

import java.util.Scanner
import java.io.File
import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout.{FlowPane, HBox, VBox}
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
  val buttonClearBD = new Button("Clear BD")
  val buttonClearAll = new Button("Clear All")
  val buttonClearItem = new Button("Clear Item")
  val buttonChange = new Button("SHOW MUST GO ON")
  val buttonSaveChanges = new Button("Save changes")

  val textFieldName = new TextField("Name")
  val textFieldNumber = new TextField("Number")

  val menuItemClose = new MenuItem("Close")
  val menuItemShowContacts = new MenuItem("Show contacts")

  val menuButtonEdit = new MenuButton("Edit", null, menuItemClose, menuItemShowContacts)

  val clearWindow = new ClearWindow()

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
        Utils.writeToDB(textFieldName.getText, textFieldNumber.getText)
      }
    })

    clearWindow.openClearWindowWithButton(buttonClearBD)

    menuItemClose.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    menuItemClose.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        primaryStage.close()
      }
    })

    menuItemShowContacts.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN))
    menuItemShowContacts.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        val listView: ListView[String] = new ListView()

        val scanner = new Scanner(new File(Utils.pathToCsvFile))
        scanner.useDelimiter(",")
        while (scanner.hasNext()) {
          val allContactsFromBD = scanner.next()
          val contact = allContactsFromBD.split(';')
          for (num <- contact.indices) {
            val allContactInfo = contact(num)
            listView.getItems.add(allContactInfo.split('|').head)
          }
        }
        scanner.close()

        val vBox = new VBox()
        vBox.getChildren.addAll(listView, buttonClearAll, buttonClearItem, buttonChange)

        buttonClearAll.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            listView.getItems.clear()
            Utils.clearDB()
          }
        })

        buttonClearItem.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            val selectItem = listView.getSelectionModel.getSelectedItem
            listView.getItems.remove(selectItem)
            val scanner = new Scanner(new File(Utils.pathToCsvFile))
            scanner.useDelimiter(",")
            while (scanner.hasNext()) {
              val allContactsFromBD = scanner.next()
              val newBase = allContactsFromBD.replaceFirst(s"$selectItem;", "") //TODO костыль шо пздц
              Utils.clearDB()
              Utils.writeStrToCSV(newBase)
            }
          }
        })

        buttonChange.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            val indexSelectItem = listView.getSelectionModel.getSelectedIndex
            val allItemsFromBD = Utils.scaninig()
            val selectItemInBD = allItemsFromBD(indexSelectItem)
            val changeStage = new Stage()
            val hb = new HBox()
            val textFieldName = new TextField(selectItemInBD.split('|').head)
            val textFieldNumber = new TextField(selectItemInBD.split('|')(1))
            hb.getChildren.addAll(textFieldName, textFieldNumber, buttonSaveChanges)

            buttonSaveChanges.setOnAction(new EventHandler[ActionEvent] {
              override def handle(e: ActionEvent): Unit = {
                val newName = textFieldName.getText
                val newNumber = textFieldNumber.getText
                val allItemsFromBD = Utils.scaninig()
                allItemsFromBD.update(indexSelectItem, s"$newName|$newNumber")
                Utils.clearDB()
                for (i <- allItemsFromBD.indices) {
                  Utils.writeStrToCSV(s"${allItemsFromBD(i)};")
                }
                changeStage.close()
              }
            })
            changeStage.setTitle("Изменить")
            changeStage.setScene(new Scene(hb, 300, 100))
            changeStage.show()
          }
        })

        stageWithContacts.setTitle("Все контакты")
        stageWithContacts.setScene(new Scene(vBox, 300, 250))
        stageWithContacts.show()
      }
    })

    buttonNewContact.setMaxWidth(Double.MaxValue)
    buttonDelete.setMaxWidth(Double.MaxValue)
    buttonClearBD.setMaxWidth(Double.MaxValue)
    pane.setOrientation(Orientation.VERTICAL)
    pane.getChildren.add(textFieldName)
    pane.getChildren.add(textFieldNumber)
    pane.getChildren.add(buttonDelete)
    pane.getChildren.add(buttonNewContact)
    pane.getChildren.add(buttonClearBD)
    pane.getChildren.add(0, new HBox(menuButtonEdit))
    primaryStage.setScene(new Scene(pane, 300, 250))
    primaryStage.show()
  }

}
