package view

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.Callback
import view.helpers.LambdaHelper._

import view.JavaFxListView.Car

object JavaFxListView {

  case class Car(var plate: String, string2: String, string3: String, d: Double)

  def main(args: Array[String]) {
    Application.launch(classOf[JavaFxListView], args: _*)
  }
}

class JavaFxListView extends Application {
  @throws[Exception]
  def start(arg0: Stage) {
    val plateList = new ListView[Car]
    plateList.setCellFactory(new Callback[ListView[Car], ListCell[Car]]() {
      def call(param: ListView[Car]): ListCell[Car] = {
        val cell = new ListCell[Car]() {
          override protected def updateItem(item: Car, empty: Boolean) {
            super.updateItem(item, empty)
            if (item != null) setText(item.plate)
            else setText("")
          }
        }
        cell
      }
    })
    val delete = new Button("Delete")
    val sample: ObservableList[Car] = FXCollections.observableArrayList()
    sample.add(Car("123-abc", "opel", "corsa", 5.5))
    sample.add(Car("123-cba", "vw", "passat", 7.5))

    delete.setOnAction((e: ActionEvent) => {
      plateList.getItems.remove(plateList.getSelectionModel.getSelectedItem)
      val t = plateList.getItems
      println(t.get(0))
      plateList.setItems(t)
    })


    plateList.setItems(sample)
    arg0.setScene(new Scene(new VBox(plateList, delete)))
    arg0.show()
  }
}