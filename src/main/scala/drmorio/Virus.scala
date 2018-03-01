package drmorio

class Virus(x: Int, y: Int) extends Entity {
  val _color = Entity.Colors(util.Random.nextInt(3))

  def shape: DrMorioShape.Value = DrMorioShape.Circle

  def locsAndColors: List[(Int, Int, Entity.Colors.Value)] = List((x, y, _color))
  def move(dx: Int, dy: Int): Boolean = false
  
  def remove(x: Int, y: Int): Option[PillPiece] = None
}