package multithreading

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object ThreeActors extends App {
  class CountActor extends Actor {
    def receive = {
      case _ =>
    }
  }
  
  
  val system = ActorSystem("ThreeFineActors")
  system.actorOf(Props(new CountActor), "Actor1")
  
}