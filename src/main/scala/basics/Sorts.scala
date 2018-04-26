package basics

object Sorts {
  def bubbleSort[A <% Ordered[A]](arr: Array[A]): Unit = {
    for (i <- 0 until arr.length) {
      for (j <- 0 until arr.length - 1 - i) {
        if (arr(j) > arr(j + 1)) {
          val tmp = arr(j)
          arr(j) = arr(j + 1)
          arr(j + 1) = tmp
        }
      }
    }
  }

  def bubbleSort2[A](arr: Array[A])(gt: (A, A) => Boolean): Unit = {
    for (i <- 0 until arr.length) {
      for (j <- 0 until arr.length - 1 - i) {
        if (gt(arr(j), arr(j + 1))) {
          val tmp = arr(j)
          arr(j) = arr(j + 1)
          arr(j + 1) = tmp
        }
      }
    }
  }

  //  def mergeSort(): ??? = {
  //    
  //  }

  def quicksort[A](lst: List[A])(lt: (A, A) => Boolean): List[A] = lst match {
    case Nil => lst
    case h :: Nil => lst
    case pivot :: t =>
      val (less, greater) = t.partition(x => lt(x, pivot))
      quicksort(less)(lt) ::: (pivot :: quicksort(greater)(lt))
  }

  def quicksort[A](arr: Array[A])(lt: (A, A) => Boolean): Unit = {
    def insertionHelper(arr: Array[A], start: Int, end: Int)(lt: (A, A) => Boolean): Unit = {
      for(i <- start+1 until end) {
        var j = i
        val tmp = arr(j)
        while(j > 0 && lt(tmp, arr(j-1))) {
          arr(j) = arr(j-1)
          j -= 1
        }
        arr(j) = tmp
      }
    }
    def helper(start: Int, end: Int): Unit = {
      if (end - start < 10) insertionHelper(arr, start, end)(lt) else {
        val pIndex = util.Random.nextInt(end - start) + start
        val tmp = arr(start)
        arr(start) = arr(pIndex)
        arr(pIndex) = tmp
        var low = start + 1
        var high = end - 1
        while (low <= high) {
          if (lt(arr(low), arr(start))) {
            low += 1
          } else {
            val tmp = arr(low)
            arr(low) = arr(high)
            arr(high) = tmp
            high -= 1
          }
        }
        val tmp2 = arr(high)
        arr(high) = arr(start)
        arr(start) = tmp2
        helper(start, high)
        helper(low, end)
      }
    }
    helper(0, arr.length)
  }

  def main(args: Array[String]): Unit = {
    val nums = Array.fill(100000)(util.Random.nextInt(100))
    println(nums.take(20).mkString(", "))
    bubbleSort(nums)
    println(nums.take(20).mkString(", "))

    val nums2 = Array.fill(10)(math.random())
    bubbleSort(nums2)

    bubbleSort("This is a test".split(" "))

    bubbleSort2(nums)(_ % 10 > _ % 10)
    println(nums.mkString(", "))
    
    val bigArray = Array.fill(2000000)(util.Random.nextInt(1000000))
    println(bigArray.take(20).mkString(" "))
    quicksort(bigArray)(_ < _)
    println(bigArray.take(20).mkString(" "))
  }
}