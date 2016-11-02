package view.helpers

import javafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import javafx.scene.layout.{AnchorPane, HBox}

import view.utils.Constants

trait MainWindowHelper {
  val rootPane = new AnchorPane()

  val buttonDeleteAll = new Button("Delete All")
  val buttonDeleteItem = new Button("Delete Item")
  val buttonEdit = new Button("Edit...")

  val menuBar = new MenuBar()

  val fileMenu = new Menu("File")
  val editMenu = new Menu("Edit")
  val helpMenu = new Menu("Help")

  val exitMenuItem = new MenuItem("Exit")
  val newMenuItem = new MenuItem("New")

  val mainButtonPanel = new HBox(5)

  val WIDTH_MAIN_SCENE = 600
  val HEIGHT_MAIN_SCENE = 300

  AnchorPane.setTopAnchor(menuBar, 0d) // TODO разобарться с переменными
  AnchorPane.setRightAnchor(mainButtonPanel, 10d)
  AnchorPane.setBottomAnchor(mainButtonPanel, 10d)
  AnchorPane.setLeftAnchor(Constants.listView, 0d)
  AnchorPane.setTopAnchor(Constants.listView, 25d)
  AnchorPane.setBottomAnchor(Constants.listView, 0d)
}
