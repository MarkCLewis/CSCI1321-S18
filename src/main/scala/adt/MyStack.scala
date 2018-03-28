package adt

trait MyStack[A] {
  def push(a: A): Unit
  def pop(): A
  def peek: A
  def isEmpty: Boolean
}