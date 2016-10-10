package view

import javafx.scene.control.ListView

import scala.compat.Platform

object Constants {

  object FileConstants {
    val FILE_NAME = "test.csv"
  }

  val LINE_BREAK = Platform.EOL

  val WIDTH_MAIN_SCENE = 600
  val HEIGHT_MAIN_SCENE = 300

  val listView: ListView[String] = new ListView()
}
