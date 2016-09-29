import java.io.{File, FileWriter}
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.{Group, Scene}
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.{Pane, StackPane}
import javafx.scene.text.Text
import javafx.stage.Stage

object HelloStageDemo {
  def main(args: Array[String]) {
    Application.launch(classOf[HelloStageDemo], args: _*)
  }
}

class HelloStageDemo extends Application {

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Новый контакт")
    val delete = new Button("Delete All")
    val save = new Button("Save")
    val clear = new Button("Clear BD")
    val btn = new Button("Say 'Hello World'")
    val firstName = new TextField("Name")
    val number = new TextField("Number")
    val label = new Label("First Name")

    btn.setOnAction(new EventHandler[ActionEvent] {
      override def handle(e: ActionEvent) {
        println("Hello World!")
      }
    })

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
        val lay = new StackPane()
        no.setLayoutX(10)
        no.setLayoutY(10)
        yes.setLayoutX(30)
        yes.setLayoutY(30)
        lay.getChildren.add(no)
        lay.getChildren.add(yes)

        val sec = new Scene(lay, 200, 100)

        val secSt = new Stage()
        secSt.setTitle("NEW")
        secSt.setScene(sec)
        secSt.show()
      }
    })

    val root = new Pane()
    btn.setLayoutX(0)
    btn.setLayoutY(0)
    firstName.setLayoutX(0)
    firstName.setLayoutY(30)
    number.setLayoutX(0)
    number.setLayoutY(60)
    label.setLayoutX(155)
    label.setLayoutY(35)
    delete.setLayoutX(0)
    delete.setLayoutY(90)
    save.setMaxWidth(50)
    save.setLayoutX(0)
    save.setLayoutY(120)
    clear.setLayoutX(0)
    clear.setLayoutY(150)
    root.getChildren.add(btn)
    root.getChildren.add(firstName)
    root.getChildren.add(number)
    root.getChildren.add(label)
    root.getChildren.add(delete)
    root.getChildren.add(save)
    root.getChildren.add(clear)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show
  }

}
