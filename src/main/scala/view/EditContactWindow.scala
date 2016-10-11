package view

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.HBox
import javafx.stage.Stage

import LambdaHelper._

class EditContactWindow {
  private val changeStage = new Stage()

  private val buttonSaveChanges = new Button("Save changes")

  private val listView = Constants.listView

  def editContact(buttonEdit : Button) = {
    buttonEdit.setOnAction((e: ActionEvent) => {
      val editContactPanel = new HBox()
      val indexSelectItem = listView.getSelectionModel.getSelectedIndex
      val allItemsFromBD = Utils.scanning()
      val selectItemInBD = allItemsFromBD(indexSelectItem)

      val textFieldName = new TextField(selectItemInBD.split('|').head)
      val textFieldNumber = new TextField(selectItemInBD.split('|')(1))
      editContactPanel.getChildren.addAll(textFieldName, textFieldNumber, buttonSaveChanges)
      buttonSaveChanges.setOnAction((e: ActionEvent) => {
        val textFromFieldName = textFieldName.getText
        val textFromFieldNumber = textFieldNumber.getText
        val allItemsFromBD = Utils.scanning()
        allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldNumber")
        Utils.clearDB()
        for (i <- allItemsFromBD.indices) {
          Utils.writeStrToCSV(allItemsFromBD(i) + Constants.LINE_BREAK)
        }
        Utils.updateList(listView)
        changeStage.close()
      })

      changeStage.setTitle("Изменить")
      changeStage.setScene(new Scene(editContactPanel, 300, 100))
      changeStage.show()
    })
  }
}
