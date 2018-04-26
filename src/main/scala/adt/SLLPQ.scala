package adt

/** A sorted, singly-linked-list priority queue**/

import reflect.ClassTag

class SLLPQ[A : ClassTag](pri:(A,A)=>Boolean) extends MyPriorityQueue[A] {
  class Node(val data: A, var prev: Node, var next: Node)
  private var default: A = _
  val end = new Node(default, null, null)
  end.next = end
  end.prev = end

  def enqueue(a:A):Unit = {
    var rover = end.prev
    while (rover != end && pri(a,rover.data)) rover = rover.prev
    rover.next.prev = new Node(a, rover, rover.next)
    rover.next = rover.next.prev
  }

  def dequeue(): A = {
    val ret = end.next.data
    end.next = end.next.next
    end.next.prev = end
    ret
  }

  def peek: A = end.next.data

  def isEmpty: Boolean = end.next == end
}