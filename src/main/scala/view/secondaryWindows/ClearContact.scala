package view.secondaryWindows

import javafx.event.ActionEvent

import view.helpers.UIHelper
import view.utils.{Constants, DataBaseUtils}

import view.helpers.LambdaHelper._

class ClearContact extends UIHelper {

  val listView = Constants.listView

  def one(): Unit = {
    val indexSelectItem = listView.getSelectionModel.getSelectedIndex
    listView.getItems.remove(indexSelectItem)
    val allItemsFromBD = DataBaseUtils.scanningDB()
    allItemsFromBD.update(indexSelectItem, "")
    DataBaseUtils.clearDB()
    for (i <- allItemsFromBD.indices) {
      if (allItemsFromBD(i).nonEmpty) {
        DataBaseUtils.writeToDB(s"${allItemsFromBD(i) + Constants.LINE_BREAK}") //TODO еще раз пересмотреть
      }
    }
  }

  def all = {
    warningStage.show()

    buttonYes.setOnAction((e: ActionEvent) => {
      DataBaseUtils.clearDB()
      listView.getItems.clear()
      warningStage.close()
    })

    buttonNo.setOnAction((e: ActionEvent) =>
      warningStage.close()
    )
  }
}
