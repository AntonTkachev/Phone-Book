package view.utils

import java.io.{File, FileWriter}
import java.util.Scanner


object DataBaseUtils extends TestTrait{

  def scanningDB() = {
    val scanner = new Scanner(new File(FILE_NAME))
    scanner.useDelimiter(",")
    var contact: Array[String] = Array()
    while (scanner.hasNext()) {
      val allContactsFromBD = scanner.next()
      contact = allContactsFromBD.split(LINE_BREAK)
    }
    contact
  }

  def writeToDB(str: String) = {
    val file = new FileWriter(new File(FILE_NAME), true)
    file.write(str)
    file.close()
  }

  def clearDB() = {
    val file = new FileWriter(new File(FILE_NAME), false)
    file.close()
  }

}
