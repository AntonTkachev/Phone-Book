package view.workWithDB

import java.sql.{Connection, DriverManager, ResultSet, Statement}

import scala.collection.mutable.ListBuffer

class ConnectTo {
  private var conn: Connection = _
  private var statmt: Statement = _
  private var resSet: ResultSet = _

  try {
    val url = "jdbc:sqlite:PersonInfo.s3db"
    conn = DriverManager.getConnection(url)
    statmt = conn.createStatement()
    statmt.executeUpdate("CREATE TABLE if not exists PersonInfo('id' INTEGER PRIMARY KEY AUTOINCREMENT, firstName varchar(10), lastName varchar(10), number varchar(10), address varchar(100));")
  } catch {
    case ex: Throwable => throw new IllegalArgumentException(ex.getMessage)
  }

  def write(personInfo: PersonInfo) = {
    statmt.executeUpdate(s"INSERT INTO PersonInfo ('firstName','lastName','number','address') VALUES('${personInfo.firstName}','${personInfo.lastName}','${personInfo.number}','${personInfo.address}');")
  }

  def selectAll(): List[PersonInfo] = {
    val resultList = new ListBuffer[PersonInfo]()
    resSet = statmt.executeQuery("SELECT * FROM PersonInfo")
    while (resSet.next()) {
      val firstName = resSet.getString("firstName")
      val lastName = resSet.getString("lastName")
      val number = resSet.getString("number")
      val address = resSet.getString("address")
      val ID = resSet.getInt("id")
      resultList += PersonInfo(Some(ID), firstName, lastName, number, address)
    }
    resultList.toList
  }

  def selectById(id: Int): PersonInfo = {
    resSet = statmt.executeQuery(s"SELECT * FROM PersonInfo WHERE id = $id")
    if (resSet.next()) {
      val firstName = resSet.getString("firstName")
      val lastName = resSet.getString("lastName")
      val number = resSet.getString("number")
      val address = resSet.getString("address")
      val ID = resSet.getInt("id")
      PersonInfo(Some(id), firstName, lastName, number, address)
    } else throw new ClassNotFoundException("id not found") //TODO maybe my exception
  }

  def close(): Unit = {
    conn.close()
    resSet.close()
  }

  def update(id: Int, personInfo: PersonInfo) = {
    statmt = conn.createStatement()
    statmt.execute(s"UPDATE PersonInfo SET firstName ='${personInfo.firstName}', lastName='${personInfo.lastName}', number ='${personInfo.number}', ADDRESS = '${personInfo.address}' WHERE id = $id")
  }

  def clearAll() = {
    statmt = conn.createStatement()
    statmt.executeUpdate("DROP TABLE PersonInfo")
  }

  def clearElement(id: Int) = {
    statmt = conn.createStatement()
    statmt.executeUpdate(s"DELETE FROM PersonInfo WHERE id = $id")
  }
}
