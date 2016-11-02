package view.utils

import java.io.{File, FileWriter}
import java.util.Scanner
import javafx.scene.control.ListView


object DataBaseUtils {

  val FILE_NAME = "test.csv"
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

  def updateList(list: ListView[String]) = {  //TODO посмотреть мб можно переделать или заменить
    list.getItems.clear()
    val allItemsFromBD = DataBaseUtils.scanningDB()
    for (num <- allItemsFromBD.indices) {
      val allContactInfo = allItemsFromBD(num)
      list.getItems.add(allContactInfo.split('|').head)
    }
  }
}
