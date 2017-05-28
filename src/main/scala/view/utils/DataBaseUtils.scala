package view.utils

import java.io.{File, FileWriter}
import java.util.Scanner
import javafx.collections.FXCollections
import javafx.scene.control.{ListCell, ListView}
import javafx.util.Callback

object DataBaseUtils {

  val FILE_NAME = "DB.csv"
  val DB_FILE = new File(FILE_NAME)

  def scanningDB() = {
    val scanner = new Scanner(DB_FILE)
    scanner.useDelimiter(",")
    var contact: Array[String] = Array()
    while (scanner.hasNext()) {
      val allContactsFromBD = scanner.next()
      contact = allContactsFromBD.split(Constants.LINE_BREAK)
    }
    contact
  }

  def writeToDB(str: String) = {
    val file = new FileWriter(DB_FILE, true)
    file.write(str)
    file.close()
  }

  def clearDB() = {
    val file = new FileWriter(DB_FILE, false)
    file.close()
  }

    def updateList(listView: ListView[IdName]) = {
    lazy val sample = FXCollections.observableArrayList[IdName]
    sample.clear()
    listView.setCellFactory(new Callback[ListView[IdName], ListCell[IdName]]() {
      override def call(param: ListView[IdName]): ListCell[IdName] = {
        val cell = new ListCell[IdName]() {
          override def updateItem(item: IdName, empty: Boolean): Unit = {
            super.updateItem(item, empty)
            if (item != null) setText(item.id)
            else setText("")
          }
        }
        cell
      }
    })
    val allItemsFromBD = DataBaseUtils.scanningDB()
    allItemsFromBD.foreach({ item =>
      val splitEl = item.split('|')
      sample.add(IdName(splitEl.head, splitEl(1))
      )
    })
    listView.setItems(sample)
  }
}
