package drmorio

class PillPiece(val grid: Grid, private val x: Int, private var y: Int, color: Entity.Colors.Value, val parent: Pill) extends Entity {
  def shape: DrMorioShape.Value = DrMorioShape.Square
  
  def locsAndColors: List[(Int, Int, Entity.Colors.Value)] = List((x, y, color))
  
  def move(dx: Int, dy: Int): Boolean = {
    require(dx == 0)
    val oldy = y
    y += dy
    if(!grid.isClear(locsAndColors)) {
      y = oldy
      false
    } else true
  }
  
  def remove(x: Int, y: Int): Option[PillPiece] = None
}