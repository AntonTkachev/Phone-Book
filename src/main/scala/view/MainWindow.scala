package view

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout._
import javafx.stage.Stage

import view.secondaryWindows.{ContactWindow, UI}

import view.helpers.LambdaHelper._
import view.helpers.MainWindowHelper
import view.utils._

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}

class MainWindow extends Application with MainWindowHelper {

  val listView = Constants.listView

  def item = listView.getSelectionModel.getSelectedItem

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    UI.updateList(listView)

    UI.clearAllContact(buttonDeleteAll)

    UI.clearContact(buttonDeleteItem)

    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val newContact = new ContactWindow
      newContact.create
    })

    buttonEdit.setOnAction((e: ActionEvent) => {
      if (item != null && item.nonEmpty) {
        val selectContact = new ContactWindow
        selectContact.edit
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

    primaryStage.setScene(new Scene(rootPane, WIDTH_MAIN_SCENE, HEIGHT_MAIN_SCENE))
    primaryStage.show()
  }

}
