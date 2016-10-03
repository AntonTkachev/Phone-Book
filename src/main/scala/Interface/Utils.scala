package Interface

import java.io.{File, FileWriter}

object Utils {

  val pathToCsvFile = "test.csv"

  val main = new MainWindow()
  val name = main.textFieldName
  val number = main.textFieldNumber

  def writeToDB() = {
    val file = new FileWriter(new File(pathToCsvFile), true)
    file.write(s"${name.getText} | ${number.getText};")
    file.close()
  }

  def clearDB() = {
    val file = new FileWriter(new File(pathToCsvFile), false)
    file.close()
  }
}
