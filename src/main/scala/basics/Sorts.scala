package basics

object Sorts {
  def bubbleSort[A <% Ordered[A]](arr: Array[A]): Unit = {
    for(i <- 0 until arr.length) {
      for(j <- 0 until arr.length-1-i) {
        if(arr(j) > arr(j+1)) {
          val tmp = arr(j)
          arr(j) = arr(j+1)
          arr(j+1) = tmp
        }
      }
    }
  }
  
  def bubbleSort2[A](arr: Array[A])(gt: (A, A) => Boolean): Unit = {
    for(i <- 0 until arr.length) {
      for(j <- 0 until arr.length-1-i) {
        if(gt(arr(j), arr(j+1))) {
          val tmp = arr(j)
          arr(j) = arr(j+1)
          arr(j+1) = tmp
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val nums = Array.fill(10)(util.Random.nextInt(100))
    println(nums.mkString(", "))
    bubbleSort(nums)
    println(nums.mkString(", "))
    
    val nums2 = Array.fill(10)(math.random())
    bubbleSort(nums2)
    
    bubbleSort("This is a test".split(" "))
    
    bubbleSort2(nums)(_%10 > _%10)
    println(nums.mkString(", "))
  }
}