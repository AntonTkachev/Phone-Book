package view.utils

import javafx.scene.control.ListView

import scala.compat.Platform

object Constants {

  val LINE_BREAK = Platform.EOL
  val listView: ListView[String] = new ListView()  //TODO разобраться с переносом в трейт
}
