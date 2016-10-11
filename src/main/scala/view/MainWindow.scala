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

  private val buttonDeleteAll = new Button("Delete All")
  private val buttonDeleteItem = new Button("Delete Item")
  private val buttonEdit = new Button("Edit...")

  private val menuBar = new MenuBar()

  private val fileMenu = new Menu("File")
  private val editMenu = new Menu("Edit")
  private val helpMenu = new Menu("Help")

  private val exitMenuItem = new MenuItem("Exit")
  private val newMenuItem = new MenuItem("New")

  private val clearWindow = new ClearAllContactWindow()

  private val mainButtonPanel = new HBox(5)

  val listView = Constants.listView
  val editWindow = new EditContactWindow
  val newContactWindow = new NewContactWindow

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    Utils.updateList(listView)

    newContactWindow.newContact(newMenuItem)

    clearWindow.clearAllContact(buttonDeleteAll)

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

    editWindow.editContact(buttonEdit)

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
