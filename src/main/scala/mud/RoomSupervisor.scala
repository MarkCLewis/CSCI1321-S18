package mud

import akka.actor.Actor
import akka.actor.Props

class RoomSupervisor extends Actor {
  val rooms = {
    val xmlData = xml.XML.loadFile("RoomData.xml")
    (xmlData \ "room").map(n => {
      val name = (n \ "@name").text
      val keyword = (n \ "@keyword").text
      val desc = (n \ "description").text
      val exits = (n \ "exits").text.split(",")
      val items = (n \ "item").map(in =>
        Item((in \ "@name").text, in.text)).toList
      keyword -> context.actorOf(Props(new Room(name, desc, exits, items)), keyword)
    }).toMap
  }

  context.children.foreach(_ ! Room.LinkExits(rooms))
  
  def receive = {
    case m => println("RoomSupervisor got bad message: " + m)
  }
}

object RoomSupervisor {
  
}