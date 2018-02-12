package adt

import org.junit.Assert._
import org.junit.Test

class TestArrayQueue {
  @Test def startsEmpty(): Unit = {
    val queue = new ArrayQueue[Int]()
    assertTrue(queue.isEmpty)
  }
  
  @Test def enqueue1dequeue(): Unit = {
    val queue = new ArrayQueue[Int]()
    queue.enqueue(6)
    assertFalse(queue.isEmpty)
    assertEquals(6, queue.peek)
    val i = queue.dequeue()
    assertEquals(6, i)
    assertTrue(queue.isEmpty)
  }

  @Test def enqueue2dequeue(): Unit = {
    val queue = new ArrayQueue[Int]()
    queue.enqueue(6)
    assertFalse(queue.isEmpty)
    assertEquals(6, queue.peek)
    queue.enqueue(8)
    assertFalse(queue.isEmpty)
    assertEquals(6, queue.peek)
    val i = queue.dequeue()
    assertEquals(6, i)
    assertFalse(queue.isEmpty)
    assertEquals(8, queue.peek)
    val j = queue.dequeue()
    assertEquals(8, j)
    assertTrue(queue.isEmpty)
  }

}