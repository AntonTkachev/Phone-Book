package Interface

import java.io.{File, FileWriter}
import java.util.Scanner
import javafx.stage.Stage


object Utils {

  val pathToCsvFile = "test.csv"

  val warningStage = new Stage()

  val layoutXForScene = 220
  val layoutYForScene = 100


  def scanning() = {
    val scanner = new Scanner(new File(Utils.pathToCsvFile))
    scanner.useDelimiter(",")
    var contact: Array[String] = Array()
    while (scanner.hasNext()) {
      val allContactsFromBD = scanner.next()
      contact = allContactsFromBD.split('\n')
    }
    contact
  }

  def writeStrToCSV(str: String) = {
    val file = new FileWriter(new File(pathToCsvFile), true)
    file.write(str)
    file.close()
  }

  def clearDB() = {
    val file = new FileWriter(new File(pathToCsvFile), false)
    file.close()
  }
}
