package view.Utils

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.{Button, ListView, MenuItem, TextField}
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout.{Pane, VBox}
import javafx.stage.Stage

import view.Utils.LambdaHelper._

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

  private val textFieldName = new TextField("Name")
  private val textFieldNumber = new TextField("Number")
  private val buttonNewContact = new Button("New...")
  private val stageWithContacts = new Stage()

  def newContact(newMenuItem: MenuItem) = {
    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val newContactPanel = new VBox()
      newContactPanel.getChildren.addAll(textFieldName, textFieldNumber, buttonNewContact)

      buttonNewContact.setOnAction((e: ActionEvent) => {
        val textFromFieldName = textFieldName.getText
        val textFromFieldNumber = textFieldNumber.getText
        if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
          UI.warningButtonOK()
        }
        else {
          val str: String = s"$textFromFieldName|${textFromFieldNumber + Constants.LINE_BREAK}"
          DataBaseUtils.writeToDB(str)
          val listView = Constants.listView
          UI.updateList(listView)
        }
      })
      val sc = new Scene(newContactPanel, 300, 250)
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
