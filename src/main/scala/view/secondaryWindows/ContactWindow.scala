package view.secondaryWindows

import javafx.event.ActionEvent

import view.helpers.LambdaHelper._
import view.helpers.ContactHelper
import view.utils._

class ContactWindow extends ContactHelper {

  lazy val textFromFieldName = textFieldFirstName.getText
  lazy val textFromFieldLastName = optional(textFieldLastName)
  lazy val textFromFieldNumber = textFieldNumber.getText
  lazy val textFromFieldAddress = optional(textFieldAddress)

  def create = {
    setTextToField(listWithTextField, names)

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
        WarningButton.ok("Заполнены не все поля")
      }
      else {
        val str = s"$textFromFieldName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|${Constants.LINE_BREAK}"
        DataBaseUtils.writeToDB(str)
        DataBaseUtils.updateList(Constants.listView)
      }
    })
  }

  def edit = {
    setTextToField(listWithTextField, arrayInfoContact)

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      val allItemsFromBD = DataBaseUtils.scanningDB()
      if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
        WarningButton.ok("Заполнены не все поля")
      }
      else {
        allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|")
        DataBaseUtils.clearDB()
        for (i <- allItemsFromBD.indices) {
          DataBaseUtils.writeToDB(allItemsFromBD(i) + Constants.LINE_BREAK)
        }
        DataBaseUtils.updateList(Constants.listView)
        changeStage.close()
      }
    })
  }

  buttonCancel.setOnAction((e: ActionEvent) => {
    changeStage.close()
  })

}