package view.secondaryWindows

import javafx.event.ActionEvent

import view.helpers.LambdaHelper._
import view.helpers.ContactHelper
import view.utils._
import view.workWithDB.{ConnectTo, PersonInfo}

class ContactWindow extends ContactHelper {

  lazy val textFromFieldName: String = textFieldFirstName.getText
  lazy val textFromFieldLastName: String = optional(textFieldLastName)
  lazy val textFromFieldNumber: String = textFieldNumber.getText
  lazy val textFromFieldAddress: String = optional(textFieldAddress)

  val connectTo = new ConnectTo()
  changeStage.show()

  def create = {
    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) WarningButton.ok("Заполнены не все поля")
      else {
        connectTo.write(PersonInfo(None, textFromFieldName, textFromFieldLastName, textFromFieldNumber, textFromFieldAddress))
        DataBaseUtils.updateList(Constants.listView)
        changeStage.close()
      }
    })
  }

  def edit = {
    val idName = Constants.listView.getItems.get(indexSelectItem)
    val editPerson = connectTo.selectById(idName.ID) //TODO обнести if else

    textFieldFirstName.setText(editPerson.firstName)
    textFieldLastName.setText(editPerson.lastName)
    textFieldNumber.setText(editPerson.number)
    textFieldAddress.setText(editPerson.address)

    rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

    buttonOK.setOnAction((e: ActionEvent) => {
      if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
        WarningButton.ok("Заполнены не все поля")
      }
      else {
        connectTo.update(idName.ID, PersonInfo(None, textFromFieldName, textFromFieldLastName, textFromFieldNumber, textFromFieldAddress))
        DataBaseUtils.updateList(Constants.listView)
        changeStage.close()
      }
    })
  }

  buttonCancel.setOnAction((e: ActionEvent) => {
    changeStage.close()
  })

}