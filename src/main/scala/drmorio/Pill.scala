package drmorio

class Pill(grid: Grid) extends Entity {
  private var x = 3
  private var y = 0
  private var dir = 0
  val interval = 1.0
  private var delaySum = 0.0
  
  private val offsets = Array(((0, 0), (1, 0)), ((0, -1), (0, 0)), ((1, 0), (0, 0)), ((0, 0), (0, -1)))
  
  val _colors = List(Entity.Colors(util.Random.nextInt(3)),
      Entity.Colors(util.Random.nextInt(3)))
  
  def shape: DrMorioShape.Value = DrMorioShape.Square
  
  def locsAndColors: List[(Int, Int, Entity.Colors.Value)] = {
    val ((ox1, oy1), (ox2, oy2)) = offsets(dir)
    List((x+ox1, y+oy1, _colors(0)), (x+ox2, y+oy2, _colors(1)))
  }
  
  def selfSupporting: Boolean = false
  
  def remove(x: Int, y: Int): Option[PillPiece] = {
    val es = locsAndColors.flatMap { case (ex, ey, c) => if(x==ex && y==ey) None else Some(new PillPiece(grid, ex, ey, c, this)) }
    Some(es.head)
  }


  def move(dx: Int, dy: Int): Boolean = {
    val oldx = x
    x += dx
    val oldy = y
    y += dy
    if(!grid.isClear(locsAndColors)) {
      x = oldx
      y = oldy
      if(dy > 0) {
    	  grid.landPill(this)
      }
      false
    } else true
  }
  
  def flip(): Unit = {
    val odir = dir
    dir = (dir + 1) % 4
    if(!grid.isClear(locsAndColors)) {
      dir = odir
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