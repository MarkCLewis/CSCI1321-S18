package basics

import java.io.FileOutputStream
import java.io.ObjectOutputStream

class ALittleClass(val x: Double, val i: Int, val str: String) extends Serializable

object SimpleSerlialize extends App {
  val alc = new ALittleClass(0.5, 42, "This is a test.")
  val oos = new ObjectOutputStream(new FileOutputStream("alc.bin"))
  oos.writeObject(alc)
  oos.close()
}