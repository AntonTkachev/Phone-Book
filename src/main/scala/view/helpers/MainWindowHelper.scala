package view.helpers

import javafx.scene.control.{Button, Menu, MenuBar, MenuItem}
import javafx.scene.layout.{AnchorPane, HBox}

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

}
