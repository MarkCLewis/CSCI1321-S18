package adt

trait MyPriorityQueue[A] {
  def enqueue(a: A): Unit
  def dequeue(): A
  def isEmpty: Boolean
  def peek: A
}