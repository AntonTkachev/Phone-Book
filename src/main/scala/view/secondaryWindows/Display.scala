package view.secondaryWindows

import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{AnchorPane, GridPane, VBox}

import view.utils.{Constants, DataBaseUtils}

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
          if (indexSelectItem >= 0) {
            val allItemsFromBD = DataBaseUtils.scanningDB()
            val selectItemInBD: String = allItemsFromBD(indexSelectItem)
            val firstName = selectItemInBD.split('|')
            labelFirstName.setText(s"First Name:             ${firstName.head}") //TODO very bad!!!
            labelLastName.setText(s"Last Name:             ${firstName(1)}")
            labelNumber.setText(s"Number:                 ${firstName(2)}")
            labelAddress.setText(s"Address:                 ${firstName(3)}")
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
