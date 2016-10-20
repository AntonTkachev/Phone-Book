package view.secondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.TextField

import view.helpers.LambdaHelper._
import view.helpers.ContactHelper
import view.utils.{DataBaseUtils, TestTrait}

class ContactWindow extends ContactHelper with TestTrait{

  def create = {
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
        val str: String = s"$textFromFieldFirstName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|$LINE_BREAK"
        DataBaseUtils.writeToDB(str)
        UI.updateList(listView)
      }
    })
  }

  def edit = {
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
        DataBaseUtils.writeToDB(allItemsFromBD(i) + LINE_BREAK)
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