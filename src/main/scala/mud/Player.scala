package mud

import akka.actor.ActorRef

class Player {
  def processCommand(command: String): Unit = {

  }

  def getFromInventory(itemName: String): Option[Item] = {
    ???
  }

  def addToInventory(item: Item): Unit = {

  }
  
  def inventoryListing(): String = {
    ???
  }
  
  def move(dir: String): Unit = {
    // Stuff
//    Room.rooms(loc).getExit(dir)
    // More stuff
  }
}

object Player {
  case class TakeExit(dest: Option[ActorRef])
}