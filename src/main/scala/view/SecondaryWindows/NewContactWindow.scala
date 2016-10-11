package view.SecondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.{Button, MenuItem, TextField}
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout.VBox
import javafx.stage.Stage

import view.Utils._

class NewContactWindow {

  private val textFieldName = new TextField("Name")
  private val textFieldNumber = new TextField("Number")
  private val buttonNewContact = new Button("New...")
  private val stageWithContacts = new Stage()

  def newContact(newMenuItem: MenuItem) = {
    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val newContactPanel = new VBox()
      newContactPanel.getChildren.addAll(textFieldName, textFieldNumber, buttonNewContact)

      buttonNewContact.setOnAction((e: ActionEvent) => {
        val textFromFieldName = textFieldName.getText
        val textFromFieldNumber = textFieldNumber.getText
        if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
          UI.warningButtonOK()
        }
        else {
          val str: String = s"$textFromFieldName|${textFromFieldNumber + Constants.LINE_BREAK}"
          DataBaseUtils.writeToDB(str)
          val listView = Constants.listView
          UI.updateList(listView)
        }
      })
      val sc = new Scene(newContactPanel, 300, 250)
      stageWithContacts.setScene(sc)
      stageWithContacts.show()
    })
  }
}
