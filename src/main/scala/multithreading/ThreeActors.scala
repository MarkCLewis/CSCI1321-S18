package multithreading

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

object ThreeActors extends App {
  case class CountDown(num: Int, other1: ActorRef, other2: ActorRef)
  
  class CountActor extends Actor {
    def receive = {
      case CountDown(n, other1, other2) =>
        if(n > 0) {
          println(self.path.name+" "+n)
          other1 ! CountDown(n-1, other2, self)
        } else system.terminate()
      case _ =>
    }
  }
  
  
  val system = ActorSystem("ThreeFineActors")
  val a1 = system.actorOf(Props(new CountActor), "Actor1")
  val a2 = system.actorOf(Props(new CountActor), "Actor2")
  val a3 = system.actorOf(Props(new CountActor), "Actor3")
  a1 ! CountDown(10, a2, a3)
}