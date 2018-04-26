package recursion

object Recursion extends App {
  val graph = Array(
    Array(0, 0, 0, 0, 0),
    Array(1, 1, 1, 0, 0),
    Array(0, 1, 0, 1, 0),
    Array(1, 0, 1, 0, 0),
    Array(0, 1, 0, 1, 0))

  import collection.mutable
  def reachable(vertex1: Int, vertex2: Int, connect: Array[Array[Int]]): Boolean = {
    def helper(v: Int, visited: mutable.Set[Int]): Boolean = {
      if (v == vertex2) true else {
        visited += v
        var ret = false
        for (v2 <- connect(v).indices; if connect(v)(v2) > 0 && !visited(v2)) {
          ret ||= helper(v2, visited)
        }
        ret
      }
    }
    helper(vertex1, mutable.Set())
  }

  println(reachable(2, 0, graph))

  def shortestPath(vertex1: Int, vertex2: Int, connect: Array[Array[Int]]): Int = {
    def helper(v: Int, visited: Set[Int]): Int = {
      if (v == vertex2) 0 else {
        val newVisited = visited + v
        var ret = 1000000000
        for (v2 <- connect(v).indices; if connect(v)(v2) > 0 && !newVisited(v2)) {
          ret = ret min helper(v2, newVisited)
        }
        ret+1
      }
    }
    helper(vertex1, Set())
  }

  println(shortestPath(2, 0, graph))

  def packBins(items: Array[Int], bins: Array[Int]): Boolean = {
    def helper(i: Int): Boolean = {
      if (i >= items.length) true
      else {
        bins.indices.exists { j =>
          if (items(i) <= bins(j)) {
            bins(j) -= items(i)
            val h = helper(i + 1)
            bins(j) += items(i)
            h
          } else false
        }
      }
    }
    helper(0)
  }

//  println(packBins(Array(6, 3, 3), Array(5, 7)))
}