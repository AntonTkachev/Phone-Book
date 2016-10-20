package view.secondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.TextField

import view.utils.LambdaHelper._
import view.utils.{Constants, DataBaseUtils, PersonHelper, UI}

class ModelPersonWindow extends PersonHelper {

  def newPerson() = {
    textFieldFirstName.setText(names.head)
    textFieldLastName.setText(names(1))
    textFieldNumber.setText(names(2))
    textFieldAddress.setText(names(3))

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      val textFromFieldFirstName = textFieldFirstName.getText
      val textFromFieldLastName = textFieldLastName.getText
      val textFromFieldNumber = textFieldNumber.getText
      val textFromFieldAddress = textFieldAddress.getText
      if (textFromFieldFirstName.isEmpty || textFromFieldNumber.isEmpty) {
        UI.warningButtonOK("Заполнены не все поля")
      }
      else {
        val str: String = s"$textFromFieldFirstName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|${Constants.LINE_BREAK}"
        DataBaseUtils.writeToDB(str)
        val listView = Constants.listView
        UI.updateList(listView)
      }
    })
  }

  def editPerson() = {
    //TODO падает если битая запись в БД
    val textFieldFirstName = new TextField(selectItemInBD.split('|').head)
    val textFieldLastName = new TextField(selectItemInBD.split('|')(1))
    val textFieldNumber = new TextField(selectItemInBD.split('|')(2))
    val textFieldAddress = new TextField(selectItemInBD.split('|')(3))

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      val textFromFieldName = textFieldFirstName.getText
      val textFromFieldLastName = textFieldLastName.getText
      val textFromFieldNumber = textFieldNumber.getText
      val textFromFieldAddress = textFieldAddress.getText
      val allItemsFromBD = DataBaseUtils.scanningDB()
      allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|")
      DataBaseUtils.clearDB()
      for (i <- allItemsFromBD.indices) {
        DataBaseUtils.writeToDB(allItemsFromBD(i) + Constants.LINE_BREAK)
      }
      UI.updateList(listView)
      changeStage.close()
    })
  }

  buttonCancel.setOnAction((e: ActionEvent) => {
    changeStage.close()
  })

  changeStage.setScene(new Scene(rootPane, 300, 300))
  changeStage.show()
}