package view.secondaryWindows

import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{AnchorPane, GridPane, VBox}

import view.utils.Constants
import view.workWithDB.ConnectTo

object Display {

  val listView = Constants.listView

  val labelFirstName = new Label()
  val labelLastName = new Label()
  val labelNumber = new Label()
  val labelAddress = new Label()

  def contact(rootPane: AnchorPane) = {
    listView.setOnMouseClicked(new EventHandler[MouseEvent]() { //TODO сделать шоркат для лямбды
      override def handle(click: MouseEvent): Unit = {
        val vbox = new VBox(5)
        val pane = new GridPane()
        if (click.getClickCount == 1) {
          val indexSelectItem = listView.getSelectionModel.getSelectedIndex
//          listView.setId("")
          if (indexSelectItem >= 0) {
            val connectTo = new ConnectTo()
            val idName = Constants.listView.getItems.get(indexSelectItem)
            val selectItemInBD = connectTo.selectById(idName.ID)
            connectTo.close()
            labelFirstName.setText(s"First Name:             ${selectItemInBD.firstName}") //TODO very bad!!!
            labelLastName.setText(s"Last Name:             ${selectItemInBD.lastName}")
            labelNumber.setText(s"Number:                 ${selectItemInBD.number}")
            labelAddress.setText(s"Address:                 ${selectItemInBD.address}")
            AnchorPane.setLeftAnchor(pane, 350d)
            AnchorPane.setTopAnchor(pane, 50d)
            pane.getChildren.clear()
            vbox.getChildren.addAll(labelFirstName, labelLastName, labelNumber, labelAddress)
            pane.getChildren.add(vbox)
            rootPane.getChildren.add(pane)
          }
        }
      }
    })
  }
}
