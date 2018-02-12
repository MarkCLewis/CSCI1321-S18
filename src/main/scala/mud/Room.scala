package mud

class Room(name: String, desc: String, exits: Array[Int], private var items: List[Item]) {

  def description(): String = {
    ???
  }

  def getExit(dir: Int): Option[Int] = {
    ???
  }

  def getItem(itemName: String): Option[Item] = {
    ???
  }

  def dropItem(item: Item): Unit = {

  }

}

object Room {
  val rooms = readRooms()

  def readRooms(): Array[Room] = {
    val xmlData = xml.XML.loadFile("RoomData.xml")
    (xmlData \ "room").map(n => {
      val name = (n \ "@name").text
      val desc = (n \ "description").text
      val exits = (n \ "exits").text.split(",").map(_.toInt)
      val items = (n \ "item").map(in =>
        Item((in \ "@name").text, in.text)).toList
      new Room(name, desc, exits, items)
    }).toArray
  }
}