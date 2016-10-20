package view.utils

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout.{AnchorPane, HBox, Pane, VBox}
import javafx.stage.Stage

import view.utils.LambdaHelper._

object UI {

  private val warningStage = new Stage()

  private val buttonNo = new Button("No")
  private val buttonYes = new Button("Yes")

  private val layoutXForButton = 115
  private val layoutYForButton = 70

  private val layoutXForScene = 220
  private val layoutYForScene = 60

  private val pane = new Pane()

  buttonNo.setLayoutX(layoutXForButton)
  buttonNo.setMinSize(layoutXForButton, layoutYForButton)
  buttonYes.setMinSize(layoutXForButton, layoutYForButton)

  pane.getChildren.add(buttonYes)
  pane.getChildren.add(buttonNo)

  warningStage.setTitle("Clear all contact?")
  warningStage.setScene(new Scene(pane, layoutXForScene, layoutYForScene))
  warningStage.setResizable(false)

  def clearAllContact(value: Button) = {
    value.setOnAction((e: ActionEvent) => {

      warningStage.show()

      buttonYes.setOnAction((e: ActionEvent) => {
        DataBaseUtils.clearDB()
        Constants.listView.getItems.clear()
        warningStage.close()
      })

      buttonNo.setOnAction((e: ActionEvent) =>
        warningStage.close()
      )
    })
  }

  val names = List("First Name", "Last Name", "Number", "Address")

  private val textFieldFirstName = new TextField(names.head)
  private val textFieldLastName = new TextField(names(1))
  private val textFieldNumber = new TextField(names(2))
  private val textFieldAddress = new TextField(names(3))
  private val buttonNewContact = new Button("New...")
  private val buttonCancel = new Button("Cancel")
  private val stageWithContacts = new Stage()

  def newContact(newMenuItem: MenuItem) = {
    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val rootPane = new AnchorPane()
      val rightTextFieldPanel = new VBox(5)

      val leftLabelPanel = new VBox(15)

      val bottomButtonPanel = new HBox(5)

      rightTextFieldPanel.getChildren.addAll(textFieldFirstName, textFieldLastName, textFieldNumber, textFieldAddress)

      names.foreach(name =>
        leftLabelPanel.getChildren.add(new Label(name))
      )

      bottomButtonPanel.getChildren.addAll(buttonNewContact, buttonCancel)

      rootPane.getChildren.addAll(leftLabelPanel, rightTextFieldPanel, bottomButtonPanel)

      buttonNewContact.setOnAction((e: ActionEvent) => {
        val textFromFieldFirstName = textFieldFirstName.getText
        val textFromFieldLastName = textFieldLastName.getText
        val textFromFieldNumber = textFieldNumber.getText
        val textFromFieldAddress = textFieldAddress.getText
        if (textFromFieldFirstName.isEmpty || textFromFieldNumber.isEmpty) {
          UI.warningButtonOK()
        }
        else {
          val str: String = s"$textFromFieldFirstName|$textFromFieldLastName|$textFromFieldNumber|$textFromFieldAddress|${Constants.LINE_BREAK}"
          DataBaseUtils.writeToDB(str)
          val listView = Constants.listView
          UI.updateList(listView)
        }
      })
      AnchorPane.setTopAnchor(leftLabelPanel, 10d)
      AnchorPane.setLeftAnchor(leftLabelPanel, 10d)
      AnchorPane.setTopAnchor(rightTextFieldPanel, 10d)
      AnchorPane.setRightAnchor(rightTextFieldPanel, 10d)
      AnchorPane.setBottomAnchor(bottomButtonPanel, 10d)
      AnchorPane.setRightAnchor(bottomButtonPanel, 10d)


      val sc = new Scene(rootPane, 300, 250)
      stageWithContacts.setScene(sc)
      stageWithContacts.show()
    })
  }

  private val warningStageWithOK = new Stage()

  private val buttonOk = new Button("Заполнены не все поля")

  warningStageWithOK.setScene(new Scene(buttonOk, layoutXForScene, 100))
  warningStageWithOK.setResizable(false)

  def warningButtonOK() = {
    warningStageWithOK.show()

    buttonOk.setOnAction((e: ActionEvent) =>
      warningStageWithOK.close()
    )
  }

  def updateList(list: ListView[String]) = {
    list.getItems.clear()
    val allItemsFromBD = DataBaseUtils.scanningDB()
    for (num <- allItemsFromBD.indices) {
      val allContactInfo = allItemsFromBD(num)
      list.getItems.add(allContactInfo.split('|').head)
    }
  }
}
