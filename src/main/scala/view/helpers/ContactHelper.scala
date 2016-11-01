package view.helpers

import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.{AnchorPane, HBox, VBox}
import javafx.stage.Stage

import view.utils.{Constants, DataBaseUtils}

trait ContactHelper {
  val changeStage = new Stage()
  val rootPane = new AnchorPane()

  lazy val listView = Constants.listView

  lazy val indexSelectItem = listView.getSelectionModel.getSelectedIndex
  lazy val allItemsFromBD = DataBaseUtils.scanningDB()
  lazy val selectItemInBD = allItemsFromBD(indexSelectItem)

  lazy val buttonOK = new Button("OK")
  lazy val buttonCancel = new Button("Cancel")

  lazy val textFieldFirstName = new TextField()
  lazy val textFieldLastName = new TextField()
  lazy val textFieldNumber = new TextField()
  lazy val textFieldAddress = new TextField()

  lazy val listWithTextField = List(textFieldFirstName,textFieldLastName,textFieldNumber,textFieldAddress)

  lazy val arrayInfoContact = selectItemInBD.split('|')

  lazy val names = Array("First Name", "Last Name", "Number", "Address")

  def optional(textField: TextField) = {
    if (textField.getText.isEmpty) {
      " "
    }
    else textField.getText()
  }

  def setTextToField(textFields: List[TextField], whatSet: Array[String]) = {
    var num = 0
    textFields.foreach({ textFiled => textFiled.setText(whatSet(num))
      num = num + 1
    })
  }

  buttonCancel.setMinSize(80, 10)
  buttonOK.setMinSize(80, 10)

  lazy val bottomButtonPanel = new HBox(5)

  lazy val leftLabelPanel = new VBox(15)
  var rightTextFieldPanel = new VBox(5)

  bottomButtonPanel.getChildren.addAll(buttonOK, buttonCancel)

  names.foreach(name =>
    leftLabelPanel.getChildren.add(new Label(name))
  )

  rootPane.getChildren.addAll(bottomButtonPanel, leftLabelPanel, rightTextFieldPanel)

  AnchorPane.setBottomAnchor(bottomButtonPanel, 10d)
  AnchorPane.setRightAnchor(bottomButtonPanel, 10d)
  AnchorPane.setTopAnchor(leftLabelPanel, 10d)
  AnchorPane.setLeftAnchor(leftLabelPanel, 10d)
  AnchorPane.setRightAnchor(rightTextFieldPanel, 10d)
  AnchorPane.setTopAnchor(rightTextFieldPanel, 10d)
}