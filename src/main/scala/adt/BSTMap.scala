package adt

import collection.mutable

class BSTMap[K, V](comp: (K, K) => Int) extends mutable.Map[K, V] {
  import BSTMap._
  private var root: Node[K, V] = null

  def get(key: K): Option[V] = {
    var rover = root
    while (rover != null && comp(rover.key, key) != 0) {
      if (comp(key, rover.key) < 0) rover = rover.left
      else rover = rover.right
    }
    if (rover == null) None else Some(rover.value)
  }

  def preorder(visitor: (K, V) => Unit): Unit = {
    def helper(n: Node[K, V]): Unit = if (n != null) {
      visitor(n.key, n.value)
      helper(n.left)
      helper(n.right)
    }
    helper(root)
  }

  def postorder(visitor: (K, V) => Unit): Unit = {
    def helper(n: Node[K, V]): Unit = if (n != null) {
      helper(n.left)
      helper(n.right)
      visitor(n.key, n.value)
    }
    helper(root)
  }

  def inorder(visitor: (K, V) => Unit): Unit = {
    def helper(n: Node[K, V]): Unit = if (n != null) {
      helper(n.left)
      visitor(n.key, n.value)
      helper(n.right)
    }
    helper(root)
  }

  def iterator = new Iterator[(K, V)] {
    private val stack = new LLStack[Node[K, V]]
    pushAllLeft(root)
    
    def pushAllLeft(n: Node[K, V]): Unit = {
      if(n != null) {
        stack.push(n)
        pushAllLeft(n.left)
      }
    }
    
    def hasNext: Boolean = !stack.isEmpty
    def next(): (K, V) = {
      val ret = stack.pop()
      pushAllLeft(ret.right)
      ret.key -> ret.value
    }
  }

  def -=(key: K) = {
    ???
    this
  }

  def +=(kv: (K, V)) = {
    def helper(n: Node[K, V]): Node[K, V] = {
      if (n == null) new Node[K, V](kv._1, kv._2, null, null)
      else {
        if (comp(kv._1, n.key) == 0) n.value = kv._2
        else if (comp(kv._1, n.key) < 0) n.left = helper(n.left)
        else n.right = helper(n.right)
        n
      }
    }
    root = helper(root)
    this
  }
}

object BSTMap {
  private class Node[K, V](val key: K, var value: V, var left: Node[K, V], var right: Node[K, V])
}