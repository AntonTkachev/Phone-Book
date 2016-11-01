package view.secondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene

import view.helpers.LambdaHelper._
import view.helpers.ContactHelper
import view.utils.{DataBaseUtils, TestTrait}

class ContactWindow extends ContactHelper with TestTrait {

  def create = {
    setTextToField(listWithTextField, names)

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      val textFromFieldName = textFieldFirstName.getText
      val textFromFieldLastName = optional(textFieldLastName)
      val textFromFieldNumber = textFieldNumber.getText
      val textFromFieldAddress = optional(textFieldAddress)
      if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
        UI.warningButtonOK("Заполнены не все поля")
      }
      else {
        val str: String = s"$textFromFieldName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|$LINE_BREAK"
        DataBaseUtils.writeToDB(str)
        UI.updateList(listView)
      }
    })
  }

  def edit = {  //TODO падает если битая запись в БД
    setTextToField(listWithTextField, arrayInfoContact)

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      val textFromFieldName = textFieldFirstName.getText
      val textFromFieldLastName = optional(textFieldLastName)
      val textFromFieldNumber = textFieldNumber.getText
      val textFromFieldAddress = optional(textFieldAddress)
      val allItemsFromBD = DataBaseUtils.scanningDB()
      if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
        UI.warningButtonOK("Заполнены не все поля")
      }
      else {
        allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|")
        DataBaseUtils.clearDB()
        for (i <- allItemsFromBD.indices) {
          DataBaseUtils.writeToDB(allItemsFromBD(i) + LINE_BREAK)
        }
        UI.updateList(listView)
        changeStage.close()
      }
    })
  }

  buttonCancel.setOnAction((e: ActionEvent) => {
    changeStage.close()
  })

  changeStage.setScene(new Scene(rootPane, 300, 300))
  changeStage.setResizable(false)
  changeStage.show()
}