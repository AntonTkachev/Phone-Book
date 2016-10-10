package view

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination}
import javafx.scene.layout._
import javafx.stage.Stage

import LambdaHelper._

object MainWindow {
  def main(args: Array[String]) {
    Application.launch(classOf[MainWindow], args: _*)
  }
}

class MainWindow extends Application {
  private val pane = new AnchorPane()

  private val stageWithContacts = new Stage()

  private val buttonNewContact = new Button("New...")
  private val buttonClearAll = new Button("Clear All")
  private val buttonClearItem = new Button("Clear Item")
  private val buttonChange = new Button("SHOW MUST GO ON")
  private val buttonSaveChanges = new Button("Save changes")

  val textFieldName = new TextField("Name")
  val textFieldNumber = new TextField("Number")

  val borderPane = new BorderPane()
  val menuBar = new MenuBar()

  val fileMenu = new Menu("File")
  val editMenu = new Menu("Edit")
  val helpMenu = new Menu("Help")

  val exitMenuItem = new MenuItem("Exit")
  val newMenuItem = new MenuItem("New...")
  val showContactsMenuItem = new MenuItem("Show contacts")

  val clearWindow = new ClearWindow()
  val warningWindow = new WarningWindow()

  val hbox = new HBox(5)
  val vb = new VBox()

  val listView = Constants.listView

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    val allItemsFromBD = Utils.scanning() //TODO сделать методом и распределить по проекту
    for (num <- allItemsFromBD.indices) {
      val allContactInfo = allItemsFromBD(num)
      listView.getItems.add(allContactInfo.split('|').head)
    }

    newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN))
    newMenuItem.setOnAction((e: ActionEvent) => {


      val vb = new VBox()
      vb.getChildren.addAll(textFieldName, textFieldNumber, buttonNewContact)

      buttonNewContact.setOnAction((e: ActionEvent) => {
        val name = textFieldName.getText
        val number = textFieldNumber.getText
        if (name.isEmpty || number.isEmpty) {
          warningWindow.warningButtonOK()
        }
        else {
          val str: String = s"${textFieldName.getText}|${textFieldNumber.getText + Constants.LINE_BREAK}"
          Utils.writeStrToCSV(str)

          listView.getItems.clear()
          val allItemsFromBD = Utils.scanning()
          for (num <- allItemsFromBD.indices) {
            val allContactInfo = allItemsFromBD(num)
            listView.getItems.add(allContactInfo.split('|').head)
          }
        }
      })
      val sc = new Scene(vb, 300, 250)
      stageWithContacts.setScene(sc)
      stageWithContacts.show()
    })

    clearWindow.openClearWindowWithButton(buttonClearAll)

    buttonClearItem.setOnAction((e: ActionEvent) => {   //todo ЛАГАЕТ КОГДА БД ПУСТА
      val indexSelectItem = listView.getSelectionModel.getSelectedIndex
      listView.getItems.remove(indexSelectItem)
      val allItemsFromBD = Utils.scanning()
      allItemsFromBD.update(indexSelectItem, "")
      Utils.clearDB()
      for (i <- allItemsFromBD.indices) {
        if (allItemsFromBD(i).nonEmpty) {
          Utils.writeStrToCSV(s"${allItemsFromBD(i) + Constants.LINE_BREAK}") //TODO еще раз пересмотреть
        }
      }
    })

    buttonChange.setOnAction((e: ActionEvent) => {
      val indexSelectItem = listView.getSelectionModel.getSelectedIndex
      val allItemsFromBD = Utils.scanning()
      val selectItemInBD = allItemsFromBD(indexSelectItem)
      val changeStage = new Stage()
      val hb = new HBox()
      val textFieldName = new TextField(selectItemInBD.split('|').head)
      val textFieldNumber = new TextField(selectItemInBD.split('|')(1))
      hb.getChildren.addAll(textFieldName, textFieldNumber, buttonSaveChanges)

      buttonSaveChanges.setOnAction((e: ActionEvent) => {
        val newName = textFieldName.getText
        val newNumber = textFieldNumber.getText
        val allItemsFromBD = Utils.scanning()
        allItemsFromBD.update(indexSelectItem, s"$newName|$newNumber")
        Utils.clearDB()
        for (i <- allItemsFromBD.indices) {
          Utils.writeStrToCSV(allItemsFromBD(i) + Constants.LINE_BREAK)
        }
        listView.getItems.clear()
        for (num <- allItemsFromBD.indices) {
          val allContactInfo = allItemsFromBD(num)
          listView.getItems.add(allContactInfo.split('|').head)
        }
        changeStage.close()
      })


      changeStage.setTitle("Изменить")
      changeStage.setScene(new Scene(hb, 300, 100))
      changeStage.show()
    })

    exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    exitMenuItem.setOnAction((e: ActionEvent) =>
      primaryStage.close()
    )

    val rootPane = new AnchorPane()

    menuBar.prefWidthProperty().bind(primaryStage.widthProperty())
    fileMenu.getItems.addAll(exitMenuItem)
    editMenu.getItems.addAll(newMenuItem)
    menuBar.getMenus.addAll(fileMenu, editMenu, helpMenu)

    vb.getChildren.addAll(listView)

    hbox.getChildren.addAll(buttonClearAll, buttonClearItem, buttonChange)
    AnchorPane.setTopAnchor(menuBar, 0d)     // TODO разобарться с переменными
    AnchorPane.setRightAnchor(hbox, 10d)
    AnchorPane.setBottomAnchor(hbox, 10d)
    AnchorPane.setLeftAnchor(listView, 0d)
    AnchorPane.setTopAnchor(listView, 25d)
    AnchorPane.setBottomAnchor(listView, 0d)
    AnchorPane.setRightAnchor(vb, 25d)
    AnchorPane.setTopAnchor(vb, 25d)

    rootPane.getChildren.addAll(menuBar, listView, hbox, vb)

    primaryStage.setScene(new Scene(rootPane, Constants.WIDTH_MAIN_SCENE, Constants.HEIGHT_MAIN_SCENE))
    primaryStage.show()
  }

}
