package view

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout._
import javafx.stage.Stage

import view.secondaryWindows.ModelPersonWindow

import view.utils.LambdaHelper._
import view.utils._

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

  private val mainButtonPanel = new HBox(5)

  val listView = Constants.listView
  def item = Constants.item

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    UI.updateList(listView)

    UI.clearAllContact(buttonDeleteAll)

    UI.clearContact(buttonDeleteItem)

    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val create = new ModelPersonWindow
      create.newPerson()
    })

    buttonEdit.setOnAction((e: ActionEvent) => {
      if (item != null && item.nonEmpty) {
        val create = new ModelPersonWindow
        create.editPerson()
      }
      else {
        UI.warningButtonOK("Не выбран контакт для изменения")
      }
    })

    exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    exitMenuItem.setOnAction((e: ActionEvent) =>
      primaryStage.close()
    )

    menuBar.prefWidthProperty().bind(primaryStage.widthProperty()) //TODO все что ниже можно в трейт
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
