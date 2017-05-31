package view.workWithDB

object TestBD extends App {
  val connectTo = new ConnectTo()
  connectTo.sqlite()
  connectTo.createTable()
  connectTo.writeDB(PersonInfo("Anton", "Tkachev", "1488", "evergreenStreet"))
  connectTo.selectAll()
  connectTo.update(8, PersonInfo("Ally", "Smith", "1939", "Kansas"))
  connectTo.update(9, PersonInfo("Ally", "Smith", "1939", "Kansas"))
//  connectTo.smallUpdate()
  connectTo.selectAll()
  connectTo.clearAll()
  connectTo.close()
}
