package adt

class LLQueue[A] extends MyQueue[A] {
  import LLQueue._
  private var front: Node[A] = null
  private var back: Node[A] = null

  def enqueue(a: A): Unit = {
    val n = new Node(a, null)
    if (isEmpty) {
      front = n
      back = n
    } else {
      back.next = n
      back = n
    }
  }

  def dequeue(): A = {
    val ret = front.data
    front = front.next
    if (front == null) back = null
    ret
  }

  def peek: A = front.data

  def isEmpty: Boolean = front == null
}

object LLQueue {
  private class Node[A](val data: A, var next: Node[A])
}