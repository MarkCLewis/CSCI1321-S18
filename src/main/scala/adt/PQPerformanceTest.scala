package adt

object PQPerformanceTest extends App {
  val n = 10000
  
  val binaryHeap = new BinaryHeapPQ[Double](_ < _)
  println("Adding Heap = "+timeAction(for(i <- 1 to n) binaryHeap.enqueue(math.random)))
  println("Removing Heap = "+timeAction(while(!binaryHeap.isEmpty) binaryHeap.dequeue()))
  
  val llpq = new SLLPQ[Double](_ < _)
  println("Adding llpq = "+timeAction(for(i <- 1 to n) llpq.enqueue(math.random)))
  println("Removing llpq = "+timeAction(while(!llpq.isEmpty) llpq.dequeue()))
  
  def timeAction(action: => Unit): Double = {
    val start = System.nanoTime()
    action
    (System.nanoTime()-start)/1e9
  }
}