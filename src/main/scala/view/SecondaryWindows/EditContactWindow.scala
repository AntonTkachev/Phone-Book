package view.SecondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.HBox
import javafx.stage.Stage

import view.Utils._

class EditContactWindow {
  private val changeStage = new Stage()

  private val buttonSaveChanges = new Button("Save changes")

  private val listView = Constants.listView

  def editContact(buttonEdit : Button) = {
    buttonEdit.setOnAction((e: ActionEvent) => { //TODO NPE отловить!!
      val editContactPanel = new HBox()
      val indexSelectItem = listView.getSelectionModel.getSelectedIndex
      val allItemsFromBD = DataBaseUtils.scanningDB()
      val selectItemInBD = allItemsFromBD(indexSelectItem)

      val textFieldName = new TextField(selectItemInBD.split('|').head)
      val textFieldNumber = new TextField(selectItemInBD.split('|')(1))
      editContactPanel.getChildren.addAll(textFieldName, textFieldNumber, buttonSaveChanges)
      buttonSaveChanges.setOnAction((e: ActionEvent) => {
        val textFromFieldName = textFieldName.getText
        val textFromFieldNumber = textFieldNumber.getText
        val allItemsFromBD = DataBaseUtils.scanningDB()
        allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldNumber")
        DataBaseUtils.clearDB()
        for (i <- allItemsFromBD.indices) {
          DataBaseUtils.writeToDB(allItemsFromBD(i) + Constants.LINE_BREAK)
        }
        UI.updateList(listView)
        changeStage.close()
      })

      changeStage.setTitle("Изменить")
      changeStage.setScene(new Scene(editContactPanel, 300, 100))
      changeStage.show()
    })
  }
}
