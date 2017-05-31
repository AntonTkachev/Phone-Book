package view.utils

import javafx.collections.FXCollections
import javafx.scene.control.{ListCell, ListView}
import javafx.util.Callback

import view.workWithDB.ConnectTo

object DataBaseUtils {
  val sample = FXCollections.observableArrayList[IdName]()

  val connectTO = new ConnectTo()

  def updateList(listView: ListView[IdName]) = {
    sample.clear()
    connectTO.selectAll().map { elem => IdName(elem.ID.get, elem.firstName) }.foreach(sample.add)
    listView.setCellFactory(new Callback[ListView[IdName], ListCell[IdName]]() {
      override def call(param: ListView[IdName]): ListCell[IdName] = {
        val cell = new ListCell[IdName]() {
          override def updateItem(item: IdName, empty: Boolean): Unit = {
            super.updateItem(item, empty)
            if (item != null) {
              setText(item.name)
            }
            else setText("")
          }
        }
        cell
      }
    })
    listView.setItems(sample)
  }
}
