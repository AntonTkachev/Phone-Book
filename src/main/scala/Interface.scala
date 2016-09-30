import java.io.{File, FileWriter}
import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.FlowPane
import javafx.stage.Stage

object Interface {
  def main(args: Array[String]) {
    Application.launch(classOf[Interface], args: _*)
  }
}

class Interface extends Application {

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")
    val delete = new Button("Delete All")
    val save = new Button("Save")
    val clear = new Button("Clear BD")

    val firstName = new TextField("Name")
    val number = new TextField("Number")

    delete.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        firstName.setText("")
        number.setText("")
      }
    })

    save.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        val pw = new FileWriter(new File("test.csv"), true)
        pw.write(s"${firstName.getText}; ${number.getText};")
        pw.close()
      }
    })

    clear.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {

        val no = new Button("No")
        val yes = new Button("Yes")
        val lay = new FlowPane()
        lay.getChildren.add(no)
        lay.getChildren.add(yes)

        val secSt = new Stage()
        secSt.setTitle("Точно хотите очистит БД?")
        secSt.setScene(new Scene(lay, 200, 100))
        secSt.show

        yes.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            new FileWriter(new File("test.csv"), false)
            secSt.close()
          }
        })
        no.setOnAction(new EventHandler[ActionEvent] {
          override def handle(e: ActionEvent) {
            secSt.close()
          }
        })
      }
    })

    val root = new FlowPane()
    save.setMaxWidth(Double.MaxValue)
    delete.setMaxWidth(Double.MaxValue)
    clear.setMaxWidth(Double.MaxValue)
    root.setOrientation(Orientation.VERTICAL)
    root.getChildren.add(firstName)
    root.getChildren.add(number)
    root.getChildren.add(delete)
    root.getChildren.add(save)
    root.getChildren.add(clear)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show
  }

}
