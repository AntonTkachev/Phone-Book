package view.secondaryWindows

import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control._

import view.helpers.LambdaHelper._
import view.helpers.UIHelper
import view.utils.{Constants, DataBaseUtils}

object UI extends UIHelper{

  def item = Constants.item

  val listView = Constants.listView

  buttonNo.setLayoutX(layoutXForButton)
  buttonNo.setMinSize(layoutXForButton, layoutYForButton)
  buttonYes.setMinSize(layoutXForButton, layoutYForButton)

  pane.getChildren.add(buttonYes)
  pane.getChildren.add(buttonNo)

  def clearAllContact(value: Button) = {
    value.setOnAction((e: ActionEvent) => {

      warningStage.show()

      buttonYes.setOnAction((e: ActionEvent) => {
        DataBaseUtils.clearDB()
        Constants.listView.getItems.clear()
        warningStage.close()
      })

      buttonNo.setOnAction((e: ActionEvent) =>
        warningStage.close()
      )
    })
  }

  def clearContact(value: Button)= {
    value.setOnAction((e: ActionEvent) => {

      if (item != null && item.nonEmpty) {
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
      else
        UI.warningButtonOK("Не выбран контакт для удаления")
    })
  }

  warningStageWithOK.setScene(new Scene(buttonOk, layoutXForScene, 100))
  warningStageWithOK.setResizable(false)

  def warningButtonOK(str : String) = {
    buttonOk.setText(str)
    warningStageWithOK.show()

    buttonOk.setOnAction((e: ActionEvent) =>
      warningStageWithOK.close()
    )
  }

  def updateList(list: ListView[String]) = {
    list.getItems.clear()
    val allItemsFromBD = DataBaseUtils.scanningDB()
    for (num <- allItemsFromBD.indices) {
      val allContactInfo = allItemsFromBD(num)
      list.getItems.add(allContactInfo.split('|').head)
    }
  }

  warningStage.setTitle("Clear all contact?")
  warningStage.setScene(new Scene(pane, layoutXForScene, layoutYForScene))
  warningStage.setResizable(false)
}
