import java.awt._
import java.awt.event._
import java.io.{File, FileWriter}
import javax.swing.{JButton, JFrame, JPanel}

class FirstFrame extends JFrame("Новый пользователь") {

  setBounds(300, 300, 300, 300)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  setLayout(new BorderLayout())
  val panel = new JPanel(new GridLayout(6, 5))
  add(panel, BorderLayout.CENTER)

  val firstName = new TextField("Name")
  val number = new TextField("Number")
  val save = new JButton("Save")
  val delete = new JButton("Delete")
  val clear = new JButton("Clear DB")
  val open = new JButton("Open directory")
  panel.add(firstName)
  panel.add(number)
  panel.add(save)
  panel.add(delete)
  panel.add(clear)
  panel.add(open)


  save.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) = {
      val pw = new FileWriter(new File("test.csv"), true)
      pw.write(s"${firstName.getText}\n ${number.getText} \n")
      pw.close()
    }
  })

  delete.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) = {
      if (firstName.getText.nonEmpty) {
        firstName.setText("")
      }
    }
  })

  clear.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) = {
      val warning = new JFrame("Warning!!")
      warning.setSize(200, 200)
      warning.setVisible(true)
      val panel = new JPanel(new GridLayout(2, 1))
      warning.add(panel, BorderLayout.CENTER)
      val yes = new JButton("Yes")
      val no = new JButton("No")
      panel.add(yes)
      panel.add(no)
      yes.addActionListener(new ActionListener {
        def actionPerformed(e: ActionEvent) = {
          new FileWriter(new File("test.csv"), false)
          warning.setVisible(false)
        }
      })
      no.addActionListener(new ActionListener {
        def actionPerformed(e: ActionEvent) = {
          warning.setVisible(false)
        }
      })
    }
  })

  open.addActionListener(new ActionListener {
    def actionPerformed(e: ActionEvent) = {
      val directory = new JFrame("Directory")
      directory.setSize(300, 300)
      directory.setVisible(true)
    }
  })

  setVisible(true)

}

object MyWindowApp extends App {
  new FirstFrame
}


