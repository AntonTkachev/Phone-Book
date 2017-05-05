package view.utils

import java.io.{File, FileWriter}
import java.util.Scanner
import javafx.collections.FXCollections
import javafx.scene.control.ListView


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

  def updateList(list: ListView[String]) = {
    val allItemsFromBD = DataBaseUtils.scanningDB()
    lazy val name = FXCollections.observableArrayList("")
    name.clear()
    allItemsFromBD.foreach(item => name.add(item.split('|').head))
    list.setItems(name)
  }
}
