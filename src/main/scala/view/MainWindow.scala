package view

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.stage.Stage

import view.secondaryWindows.{Display, UI}

import view.helpers.LambdaHelper._
import view.helpers.MainWindowHelper
import view.utils.{Constants, DataBaseUtils}

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}

class MainWindow extends Application with MainWindowHelper {

  override def start(primaryStage: Stage) { //TODO вынести primaryStage
    primaryStage.setTitle("Новый контакт")

    DataBaseUtils.updateList(Constants.listView)

    UI.clearAllContact(buttonDeleteAll)

    UI.clearContact(buttonDeleteItem)

    UI.newContact(newMenuItem)

    UI.editContact(buttonEdit)

    Display.contact(rootPane)

    exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    exitMenuItem.setOnAction((e: ActionEvent) =>
      primaryStage.close()
    )

    menuBar.prefWidthProperty().bind(primaryStage.widthProperty()) //TODO все что ниже можно в трейт
    fileMenu.getItems.addAll(exitMenuItem)
    editMenu.getItems.addAll(newMenuItem)
    menuBar.getMenus.addAll(fileMenu, editMenu, helpMenu)

    mainButtonPanel.getChildren.addAll(buttonDeleteAll, buttonDeleteItem, buttonEdit) //TODO обернуть в пейн?

    rootPane.getChildren.addAll(menuBar, Constants.listView, mainButtonPanel)

    primaryStage.setScene(new Scene(rootPane, WIDTH_MAIN_SCENE, HEIGHT_MAIN_SCENE))
    primaryStage.setResizable(false)
    primaryStage.show()
  }

}
