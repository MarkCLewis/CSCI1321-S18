package multithreading

import akka.actor.Actor
import akka.pattern._
import scala.concurrent.duration._
import akka.util.Timeout

/**
 * This actor represents one pixel that can float around until it sticks in place.
 */
class CrystalFloaty extends Actor {
  implicit val timeout = new Timeout(1.seconds)
  implicit val ec = context.dispatcher
  
  import CrystalFloaty._
  def receive = {
    case SetPosition(x, y) =>
      val mx = x + util.Random.nextInt(3)-1
      val my = y + util.Random.nextInt(3)-1
      val f = context.parent ? CrystalSupervisor.CanMoveTo(mx, my)
      f.foreach { case canMove: Boolean =>
        if(canMove) self ! SetPosition(mx, my)
        else context.parent ! CrystalSupervisor.AddCrystal(x, y)
      }
    case m =>
      println("Got a message floaty doesn't process: m")
  }
}

/**
 * I put the messages that can be sent to an actor in the companion object for that actor.
 * This isn't required, but it helps organize things and makes it more clear.
 */
object CrystalFloaty {
  /**
   * Alter the location of the Floaty.
   */
  case class SetPosition(nx: Int, ny: Int)  
}