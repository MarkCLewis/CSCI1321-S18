package basics

import io.StdIn._

/**
 * @author mlewis
 */
object REAddition extends App {
  val prods1 = Map[String, String](
    "N+N1" -> "SN+'NL1",

    // Successor
    "0S" -> "1",
    "1S" -> "S0",
    "NS" -> "N1",

    // Predecessor
    "0P" -> "P1",
    "1P" -> "R0",

    // Left Message
    "L1" -> "1L",
    "L0" -> "0L",
    "LN" -> "PN",

    // Right Message
    "1R" -> "R1",
    "0R" -> "R0",

    // Clean-up
    "+NN" -> "",
    "N0" -> "N",
    "+'NR" -> "+N")

  def advance(str: String, prods: Map[String, String]): (String, Boolean) = {
    def indices(s: String, i: Int): List[Int] = {
      val next = str.indexOf(s, i)
      if (next < 0) Nil else next :: indices(s, next + 1)
    }
    val allowed = prods.filter(t => str.contains(t._1)).toArray
    if (allowed.isEmpty) (str, false) else {
      val picked = allowed(util.Random.nextInt(allowed.length))
      val occurs = indices(picked._1, 0).toArray
      val where = occurs(util.Random.nextInt(occurs.length))
      (str.patch(where, picked._2, picked._1.length), true)
    }
  }

  println("Enter two numbers to add.")
  var str = s"N${readInt.toBinaryString}N+N${readInt.toBinaryString}N"
  var flag = true
  while (flag) {
    println(str)
    val (newstr, f) = advance(str, prods1)
    str = newstr
    flag = f
  }
  println(str)

}