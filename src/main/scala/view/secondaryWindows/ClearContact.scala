package view.secondaryWindows

import javafx.event.ActionEvent

import view.helpers.UIHelper
import view.utils.Constants
import view.helpers.LambdaHelper._
import view.workWithDB.ConnectTo

class ClearContact extends UIHelper {

  val listView = Constants.listView

  def connectTo = new ConnectTo()

  def one : Unit = {
    val indexSelectItem = listView.getSelectionModel.getSelectedIndex
    val idName = listView.getItems.get(indexSelectItem)
    listView.getItems.remove(indexSelectItem)
    connectTo.clearElement(idName.ID)
  }

  def all = {
    warningStage.show()

    buttonYes.setOnAction((e: ActionEvent) => {
      listView.getItems.clear()
      connectTo.clearAll()
      warningStage.close()
    })

    buttonNo.setOnAction((e: ActionEvent) =>
      warningStage.close()
    )
  }
}
