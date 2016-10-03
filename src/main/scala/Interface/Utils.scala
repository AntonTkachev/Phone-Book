package Interface

import java.io.{File, FileWriter}

object Utils {

  val pathToCsvFile = "test.csv"

  def writeToDB(name: String, number: String) = {
    val file = new FileWriter(new File(pathToCsvFile), true)
    file.write(s"$name | $number;")
    file.close()
  }

  def clearDB() = {
    val file = new FileWriter(new File(pathToCsvFile), false)
    file.close()
  }
}
