package mud

import akka.actor.Actor
import akka.actor.ActorRef

class Room(name: String, desc: String, 
    exitNames: Array[String], private var items: List[Item]) extends Actor {
  
  private var exits: Array[ActorRef] = null
  
  import Room._
  def receive = {
    case LinkExits(rooms) => exits = exitNames.map(rooms)
    case GetExit(dir) => sender ! Player.TakeExit(getExit(dir))
    case m => println("Room got bad message: " + m)
  }

  def description(): String = {
    ???
  }

  def getExit(dir: Int): Option[ActorRef] = {
    ???
  }

  def getItem(itemName: String): Option[Item] = {
    ???
  }

  def dropItem(item: Item): Unit = {

  }

}

object Room {
  case class LinkExits(rooms: Map[String, ActorRef])
  case class GetExit(dir: Int)
}