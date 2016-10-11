package view.SecondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.{AnchorPane, HBox, VBox}
import javafx.stage.Stage

import view.Utils.LambdaHelper._
import view.Utils._

class EditContactWindow {
  private val changeStage = new Stage()

  private val buttonSaveChanges = new Button("Save changes")
  private val buttonCancel = new Button("Cancel")

  private val labelFirstName = new Label("First Name")
  private val labelLastName = new Label("Last Name")
  private val labelNumber = new Label("Number")
  private val listView = Constants.listView

  def editContact(buttonEdit: Button) = {
    buttonEdit.setOnAction((e: ActionEvent) => {
      val item = listView.getSelectionModel.getSelectedItem
      if (item != null && item.nonEmpty) {
        val editPane = new AnchorPane()

        val indexSelectItem = listView.getSelectionModel.getSelectedIndex
        val allItemsFromBD = DataBaseUtils.scanningDB()
        val selectItemInBD = allItemsFromBD(indexSelectItem)

        val textFieldName = new TextField(selectItemInBD.split('|').head)
        val textFieldNumber = new TextField(selectItemInBD.split('|')(1))
        val textFieldLastName = new TextField(selectItemInBD.split('|')(2))

        val bottomButtonPanel = new HBox(5)
        val leftLabelPanel = new VBox(15)

        val rightTextFieldPanel = new VBox(5)

        rightTextFieldPanel.getChildren.addAll(textFieldName, textFieldLastName, textFieldNumber)
        buttonCancel.setMinSize(80, 10)
        buttonSaveChanges.setMinSize(80, 10)
        bottomButtonPanel.getChildren.addAll(buttonSaveChanges, buttonCancel)

        leftLabelPanel.getChildren.addAll(labelFirstName, labelLastName, labelNumber)

        buttonSaveChanges.setOnAction((e: ActionEvent) => {
          val textFromFieldName = textFieldName.getText
          val textFromFieldLastName = textFieldLastName.getText
          val textFromFieldNumber = textFieldNumber.getText
          val allItemsFromBD = DataBaseUtils.scanningDB()
          allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldLastName|$textFromFieldNumber")
          DataBaseUtils.clearDB()
          for (i <- allItemsFromBD.indices) {
            DataBaseUtils.writeToDB(allItemsFromBD(i) + Constants.LINE_BREAK)
          }
          UI.updateList(listView)
          changeStage.close()
        })

        buttonCancel.setOnAction((e: ActionEvent) => {
          changeStage.close()
        })

        AnchorPane.setBottomAnchor(bottomButtonPanel, 10d)
        AnchorPane.setRightAnchor(bottomButtonPanel, 10d)
        AnchorPane.setTopAnchor(leftLabelPanel, 10d)
        AnchorPane.setLeftAnchor(leftLabelPanel, 10d)
        AnchorPane.setRightAnchor(rightTextFieldPanel, 10d)
        AnchorPane.setTopAnchor(rightTextFieldPanel, 10d)

        editPane.getChildren.addAll(bottomButtonPanel, leftLabelPanel, rightTextFieldPanel)

        changeStage.setTitle("Edit Person")
        changeStage.setScene(new Scene(editPane, 300, 300))
        changeStage.show()
      }
    })
  }
}
