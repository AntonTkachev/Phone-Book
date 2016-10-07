package view

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
  private val pane = new FlowPane()

  private val stageWithContacts = new Stage()

  private val buttonDelete = new Button("Delete All")
  private val buttonNewContact = new Button("Add new contact")
  private val buttonClearBD = new Button("Clear BD")
  private val buttonClearAll = new Button("Clear All")
  private val buttonClearItem = new Button("Clear Item")
  private val buttonChange = new Button("SHOW MUST GO ON")
  private val buttonSaveChanges = new Button("Save changes")

  private val textFieldName = new TextField("Name")
  private val textFieldNumber = new TextField("Number")

  private val menuItemClose = new MenuItem("Close")
  private val menuItemShowContacts = new MenuItem("Show contacts")

  private val menuButtonEdit = new MenuButton("Edit", null, menuItemClose, menuItemShowContacts)

  private val clearWindow = new ClearWindow()
  private val warningWindow = new WarningWindow()

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    buttonDelete.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        textFieldName.setText("")
        textFieldNumber.setText("")
      }
    })

    //лямбды
    buttonNewContact.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        val name = textFieldName.getText
        val number = textFieldNumber.getText
        if (name.isEmpty || number.isEmpty) {
          warningWindow.warningButtonOK()
        }
        else {
          val str: String = s"${textFieldName.getText}|${textFieldNumber.getText+Constants.LINE_BREAK}"
          Utils.writeStrToCSV(str)
        }
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

        val allItemsFromBD = Utils.scanning()
        for (num <- allItemsFromBD.indices) {
          val allContactInfo = allItemsFromBD(num)
          listView.getItems.add(allContactInfo.split('|').head)
        }

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
            val indexSelectItem = listView.getSelectionModel.getSelectedIndex
            listView.getItems.remove(indexSelectItem)
            val allItemsFromBD = Utils.scanning()
            allItemsFromBD.update(indexSelectItem, "")
            Utils.clearDB()
            for (i <- allItemsFromBD.indices) {
              if (allItemsFromBD(i).nonEmpty) {
                Utils.writeStrToCSV(s"${allItemsFromBD(i)+Constants.LINE_BREAK}") //TODO еще раз пересмотреть
              }
            }
          }
        })

        buttonChange.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            val indexSelectItem = listView.getSelectionModel.getSelectedIndex
            val allItemsFromBD = Utils.scanning()
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
                val allItemsFromBD = Utils.scanning()
                allItemsFromBD.update(indexSelectItem, s"$newName|$newNumber")
                Utils.clearDB()
                for (i <- allItemsFromBD.indices) {
                  Utils.writeStrToCSV(allItemsFromBD(i)+Constants.LINE_BREAK)
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
