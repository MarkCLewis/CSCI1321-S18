package multithreading

import akka.actor.Actor
import scalafx.scene.image.PixelWriter
import scalafx.scene.image.PixelReader
import akka.actor.Props
import scalafx.scene.paint.Color
import scalafx.application.Platform

/**
 * This is the supervisor for the crystal growth. All the floaties will be children of this actor.
 * The interaction with the image is contained in this class so that there can't be race conditions. 
 */
class CrystalSupervisor(width: Int, height: Int, pr: PixelReader, pw: PixelWriter) extends Actor {
  for(_ <- 1 to 100) {
    context.actorOf(Props[CrystalFloaty])
  }
  for(child <- context.children) {
    child ! CrystalFloaty.SetPosition(width/2, height/2)
  }
  
  import CrystalSupervisor._
  def receive = {
    // TODO - handle messages
    case m =>
      println(s"Got a message supervisor doesn't process: $m")
  }
}

/**
 * I put the messages that can be sent to an actor in the companion object for that actor.
 * This isn't required, but it helps organize things and makes it more clear.
 */
object CrystalSupervisor {
  case class CanMoveTo(x: Int, y: Int)
  case class SetPixel(x: Int, y: Int)
}