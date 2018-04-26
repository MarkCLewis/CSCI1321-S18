package adt

import scala.reflect.ClassTag

class BinaryHeapPQ[A: ClassTag](higherP: (A, A) => Boolean) extends MyPriorityQueue[A] {
  private var heap = new Array[A](10)
  private var back = 1

  def enqueue(a: A): Unit = {
    if (back >= heap.length) {
      val tmp = new Array[A](heap.length*2)
      for(i <- heap.indices) tmp(i) = heap(i)
      heap = tmp
    }
    var bubble = back
    while (bubble > 1 && higherP(a, heap(bubble / 2))) {
      heap(bubble) = heap(bubble / 2)
      bubble /= 2
    }
    heap(bubble) = a
    back += 1
  }

  def dequeue(): A = {
    var stone = 1
    val ret = heap(1)
    back -= 1
    val tmp = heap(back)
    var flag = true
    while(stone*2 < back && flag) {
      var higherChild = stone*2
      if(higherChild+1 < back && higherP(heap(higherChild+1), heap(higherChild))) 
          higherChild += 1
      if(higherP(heap(higherChild), tmp)) {
        heap(stone) = heap(higherChild)
        stone = higherChild
      } else {
        flag = false
      }
    }
    heap(stone) = tmp
    ret
  }

  def isEmpty: Boolean = back == 1

  def peek: A = heap(1)
}