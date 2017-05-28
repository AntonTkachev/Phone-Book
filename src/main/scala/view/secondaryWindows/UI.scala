package view.secondaryWindows

import javafx.event.ActionEvent
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}

import view.helpers.LambdaHelper._
import view.utils.{Constants, WarningButton}

object UI {

  def item = listView.getSelectionModel.getSelectedItem

  val listView = Constants.listView

  def clearAllContact(value: Button) = {
    value.setOnAction((e: ActionEvent) => {
      val clearContact = new ClearContact
      clearContact.all
    })
  }

  def clearContact(value: Button) = {
    value.setOnAction((e: ActionEvent) => {
      if (item != null && item.name.nonEmpty) {
        val clearContact = new ClearContact
        clearContact.one()
      }
      else
        WarningButton.ok("Не выбран контакт для удаления")
    })
  }

  def newContact(newMenuItem: MenuItem) = {
    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {
      val newContact = new ContactWindow
      newContact.create
    })
  }

  def editContact(buttonEdit: Button) = {
    buttonEdit.setOnAction((e: ActionEvent) => {
      if (item != null && item.name.nonEmpty) {
        val selectContact = new ContactWindow
        selectContact.edit
      }
      else {
        WarningButton.ok("Не выбран контакт для изменения")
      }
    })
  }
}