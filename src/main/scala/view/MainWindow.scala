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

  private val buttonDelete = new Button("Delete All")
  private val buttonNewContact = new Button("Add new contact")
  private val buttonClearBD = new Button("Clear BD")
  private val buttonClearAll = new Button("Clear All")
  private val buttonClearItem = new Button("Clear Item")
  private val buttonChange = new Button("SHOW MUST GO ON")
  private val buttonSaveChanges = new Button("Save changes")

  private val textFieldName = new TextField("Name")
  private val textFieldNumber = new TextField("Number")

  val borderPane = new BorderPane()
  val menuBar = new MenuBar()

  val fileMenu = new Menu("File")
  val editMenu = new Menu("Edit")
  val helpMenu = new Menu("Help")

  val exitMenuItem = new MenuItem("Exit")
  val showContactsMenuItem = new MenuItem("Show contacts")

  private val clearWindow = new ClearWindow()
  private val warningWindow = new WarningWindow()

  val hbox = new HBox(5)
  val vb = new VBox()

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")

    buttonDelete.setOnAction((e: ActionEvent) => {
        textFieldName.setText("")
        textFieldNumber.setText("")
    })

    buttonNewContact.setOnAction((e: ActionEvent) => {

        val name = textFieldName.getText
        val number = textFieldNumber.getText
        if (name.isEmpty || number.isEmpty) {
          warningWindow.warningButtonOK()
        }
        else {
          val str: String = s"${textFieldName.getText}|${textFieldNumber.getText+Constants.LINE_BREAK}"
          Utils.writeStrToCSV(str)
        }
    })

    clearWindow.openClearWindowWithButton(buttonClearBD)

    exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN))
    exitMenuItem.setOnAction((e: ActionEvent) =>
        primaryStage.close()
    )

    showContactsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN))
    showContactsMenuItem.setOnAction((e: ActionEvent) => {

        val listView: ListView[String] = new ListView()

        val allItemsFromBD = Utils.scanning()
        for (num <- allItemsFromBD.indices) {
          val allContactInfo = allItemsFromBD(num)
          listView.getItems.add(allContactInfo.split('|').head)
        }

        val vBox = new VBox()
        vBox.getChildren.addAll(listView, buttonClearAll, buttonClearItem, buttonChange)

        buttonClearAll.setOnAction((e: ActionEvent) => {
            listView.getItems.clear()
            Utils.clearDB()
        })

        buttonClearItem.setOnAction((e: ActionEvent) => {
            val indexSelectItem = listView.getSelectionModel.getSelectedIndex
            listView.getItems.remove(indexSelectItem)
            val allItemsFromBD = Utils.scanning()
            allItemsFromBD.update(indexSelectItem, "")
            Utils.clearDB()
            for (i <- allItemsFromBD.indices) {
              if (allItemsFromBD(i).nonEmpty) {
                Utils.writeStrToCSV(s"${allItemsFromBD(i)+Constants.LINE_BREAK}") //TODO еще раз пересмотреть
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
                  Utils.writeStrToCSV(allItemsFromBD(i)+Constants.LINE_BREAK)
                }
                changeStage.close()
            })
            changeStage.setTitle("Изменить")
            changeStage.setScene(new Scene(hb, 300, 100))
            changeStage.show()
        })

        stageWithContacts.setTitle("Все контакты")
        stageWithContacts.setScene(new Scene(vBox, 300, 250))
        stageWithContacts.show()
    })



    menuBar.prefWidthProperty().bind(primaryStage.widthProperty())
    borderPane.setTop(menuBar)

    fileMenu.getItems.addAll(exitMenuItem,showContactsMenuItem)

    menuBar.getMenus.addAll(fileMenu,editMenu,helpMenu)

    vb.getChildren.addAll(textFieldName,textFieldNumber)

    hbox.getChildren.addAll(buttonDelete, buttonNewContact, buttonClearBD)
    pane.getChildren.add(vb)
    pane.getChildren.add(hbox)
    pane.getChildren.add(borderPane)
    AnchorPane.setTopAnchor(vb, 30d)
    AnchorPane.setRightAnchor(hbox, 10d)
    AnchorPane.setBottomAnchor(hbox, 10d)

    primaryStage.setScene(new Scene(pane, 300, 250))
    primaryStage.show()
  }

}
