package networking

import akka.actor.Actor
import java.io.PrintStream
import java.io.BufferedReader
import java.net.Socket

class Chatter(name: String, sock: Socket, in: BufferedReader, out: PrintStream) extends Actor {
  import Chatter._
  def receive = {
    case ProcessInput =>
      if(in.ready()) {
        val input = in.readLine()
        ChatMain.chatSuper ! ChatSupervisor.SendMessage(s"$name said $input")
      }
    case PrintMessage(msg) =>
      out.println(msg)
    case m => println(s"Bad message in Chatter: $m")
  }
}

object Chatter {
  case object ProcessInput
  case class PrintMessage(msg: String)
}