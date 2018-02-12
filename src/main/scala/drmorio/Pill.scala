package drmorio

class Pill(grid: Grid) extends Entity {
  private var x = 3
  private var y = 0
  val interval = 1.0
  private var delaySum = 0.0
  
  val _colors = List(Block.Colors(util.Random.nextInt(3)),
      Block.Colors(util.Random.nextInt(3)))
  
  def colors: List[Block.Colors.Value] = _colors
  
  def locations: List[(Int, Int)] = List((x, y), (x+1, y))
  
  def selfSupporting: Boolean = false

  def move(dx: Int, dy: Int): Unit = {
    val oldx = x
    x += dx
    val oldy = y
    y += dy
    if(!locations.forall(p => p._1 >= 0 && p._1 < 8) || !grid.isClear(locations)) {
      x = oldx
    }
    if(dy > 0 && y>=16 || !grid.isClear(locations)) {
      y = oldy
    }
  }
  
  def update(delay: Double): Unit = {
    delaySum += delay
    if(delaySum >= interval) {
      move(0, 1)
      delaySum = 0.0
    }
  }
}