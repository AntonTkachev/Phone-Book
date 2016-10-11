package view.Utils

import java.io.{File, FileWriter}
import java.util.Scanner


object DataBaseUtils {

  def scanningDB() = {
    val scanner = new Scanner(new File(Constants.FileConstants.FILE_NAME))
    scanner.useDelimiter(",")
    var contact: Array[String] = Array()
    while (scanner.hasNext()) {
      val allContactsFromBD = scanner.next()
      contact = allContactsFromBD.split(Constants.LINE_BREAK)
    }
    contact
  }

  def writeToDB(str: String) = {
    val file = new FileWriter(new File(Constants.FileConstants.FILE_NAME), true)
    file.write(str)
    file.close()
  }

  def clearDB() = {
    val file = new FileWriter(new File(Constants.FileConstants.FILE_NAME), false)
    file.close()
  }

}