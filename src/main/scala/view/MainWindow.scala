package view

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout._
import javafx.stage.Stage

import LambdaHelper._

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}

class MainWindow extends Application {
  private val rootPane = new AnchorPane()

  private val stageWithContacts = new Stage()
  private val changeStage = new Stage()

  private val buttonNewContact = new Button("New...")
  private val buttonDeleteAll = new Button("Delete All")
  private val buttonDeleteItem = new Button("Delete Item")
  private val buttonEdit = new Button("Edit...")
  private val buttonSaveChanges = new Button("Save changes")

  private val textFieldName = new TextField("Name")
  private val textFieldNumber = new TextField("Number")

  private val menuBar = new MenuBar()

  private val fileMenu = new Menu("File")
  private val editMenu = new Menu("Edit")
  private val helpMenu = new Menu("Help")

  private val exitMenuItem = new MenuItem("Exit")
  private val newMenuItem = new MenuItem("New")

  private val clearWindow = new ClearWindow()
  private val warningWindow = new WarningWindow()

  private val mainButtonPanel = new HBox(5)

  val listView = Constants.listView

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    Utils.updateList(listView)

    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val newContactPanel = new VBox()
      newContactPanel.getChildren.addAll(textFieldName, textFieldNumber, buttonNewContact)

      buttonNewContact.setOnAction((e: ActionEvent) => {
        val textFromFieldName = textFieldName.getText
        val textFromFieldNumber = textFieldNumber.getText
        if (textFromFieldName.isEmpty || textFromFieldNumber.isEmpty) {
          warningWindow.warningButtonOK()
        }
        else {
          val str: String = s"$textFromFieldName|${textFromFieldNumber + Constants.LINE_BREAK}"
          Utils.writeStrToCSV(str)
          val listView = Constants.listView
          Utils.updateList(listView)
        }
      })
      val sc = new Scene(newContactPanel, 300, 250)
      stageWithContacts.setScene(sc)
      stageWithContacts.show()
    })

    clearWindow.openClearWindowWithButton(buttonDeleteAll)

    buttonDeleteItem.setOnAction((e: ActionEvent) => {
      val item = listView.getSelectionModel.getSelectedItem
      if (item != null && item.nonEmpty) {
        val indexSelectItem = listView.getSelectionModel.getSelectedIndex
        listView.getItems.remove(indexSelectItem)
        val allItemsFromBD = Utils.scanning()
        allItemsFromBD.update(indexSelectItem, "")
        Utils.clearDB()
        for (i <- allItemsFromBD.indices) {
          if (allItemsFromBD(i).nonEmpty) {
            Utils.writeStrToCSV(s"${allItemsFromBD(i) + Constants.LINE_BREAK}") //TODO еще раз пересмотреть
          }
        }
      }
    })

    buttonEdit.setOnAction((e: ActionEvent) => {
      val editContactPanel = new HBox()
      val indexSelectItem = listView.getSelectionModel.getSelectedIndex
      val allItemsFromBD = Utils.scanning()
      val selectItemInBD = allItemsFromBD(indexSelectItem)

      val textFieldName = new TextField(selectItemInBD.split('|').head)
      val textFieldNumber = new TextField(selectItemInBD.split('|')(1))
      editContactPanel.getChildren.addAll(textFieldName, textFieldNumber, buttonSaveChanges)
      buttonSaveChanges.setOnAction((e: ActionEvent) => {
        val textFromFieldName = textFieldName.getText
        val textFromFieldNumber = textFieldNumber.getText
        val allItemsFromBD = Utils.scanning()
        allItemsFromBD.update(indexSelectItem, s"$textFromFieldName|$textFromFieldNumber")
        Utils.clearDB()
        for (i <- allItemsFromBD.indices) {
          Utils.writeStrToCSV(allItemsFromBD(i) + Constants.LINE_BREAK)
        }
        Utils.updateList(listView)
        changeStage.close()
      })


      changeStage.setTitle("Изменить")
      changeStage.setScene(new Scene(editContactPanel, 300, 100))
      changeStage.show()
    })

    exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    exitMenuItem.setOnAction((e: ActionEvent) =>
      primaryStage.close()
    )

    menuBar.prefWidthProperty().bind(primaryStage.widthProperty())
    fileMenu.getItems.addAll(exitMenuItem)
    editMenu.getItems.addAll(newMenuItem)
    menuBar.getMenus.addAll(fileMenu, editMenu, helpMenu)

    mainButtonPanel.getChildren.addAll(buttonDeleteAll, buttonDeleteItem, buttonEdit) //TODO обернуть в пейн?
    AnchorPane.setTopAnchor(menuBar, 0d) // TODO разобарться с переменными
    AnchorPane.setRightAnchor(mainButtonPanel, 10d)
    AnchorPane.setBottomAnchor(mainButtonPanel, 10d)
    AnchorPane.setLeftAnchor(listView, 0d)
    AnchorPane.setTopAnchor(listView, 25d)
    AnchorPane.setBottomAnchor(listView, 0d)

    rootPane.getChildren.addAll(menuBar, listView, mainButtonPanel)

    primaryStage.setScene(new Scene(rootPane, Constants.WIDTH_MAIN_SCENE, Constants.HEIGHT_MAIN_SCENE))
    primaryStage.show()
  }

}
