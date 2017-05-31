package view.workWithDB

import java.sql.{Connection, DriverManager, ResultSet, Statement}

import scala.collection.mutable.ListBuffer

class ConnectTo {
  private var conn: Connection = _
  private var statmt: Statement = _
  private var resSet: ResultSet = _

  def sqlite() {
    try {
      val url = "jdbc:sqlite:PersonInfo.s3db"
      conn = DriverManager.getConnection(url)
    } catch {
      case ex: Throwable => println(ex.getMessage)
    }
    //    finally {
    //      try {
    //        if (conn != null) conn.close()
    //      } catch {
    //        case ex: Throwable => println(ex.getMessage)
    //      }
    //    }
  }

  def createTable() = {
    statmt = conn.createStatement()
    //TODO ключ увеличивается не смотря на состояние БД либо я неправильно ее чистю
    statmt.execute("CREATE TABLE if not exists PersonInfo('id' INTEGER PRIMARY KEY AUTOINCREMENT, firstName varchar(10), lastName varchar(10), number varchar(10), address varchar(100));")
  }

  def writeDB(personInfo: PersonInfo) = {
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
      println(resSet.getInt("id"))
      resultList += PersonInfo(firstName, lastName, number, address)
    }
    resultList.toList
  }

  def close(): Unit = {
    conn.close()
    //    statmt.close()
    resSet.close()
  }

  def update(id: Int, personInfo: PersonInfo) = {
    statmt = conn.createStatement()
    statmt.execute(s"UPDATE PersonInfo SET firstName ='${personInfo.firstName}', lastName='${personInfo.lastName}', number ='${personInfo.number}', ADDRESS = '${personInfo.address}' WHERE id = $id")
    //    statmt.executeUpdate(s"UPDATE PersonInfo SET firstName ='${personInfo.firstName}' WHERE ID = $id")
  }

  def smallUpdate() = {
    statmt.executeUpdate("UPDATE PersonInfo SET ADDRESS = 'Texas' WHERE 'id' = 1")
  }

  def clearAll() = {
    statmt = conn.createStatement()
    statmt.executeUpdate("DELETE FROM PersonInfo;")
  }

  def clearElement = ??? //TODO сделать
}
