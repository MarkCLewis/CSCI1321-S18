package adt

trait MySeq[A] {
  def apply(index: Int): A
  def update(index: Int, a: A): Unit
  def add(index: Int, a: A): Unit
  def remove(index: Int): A
  def length: Int
  
  def size = length
}