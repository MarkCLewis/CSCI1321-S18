package drmorio

trait Entity {
  def shape: DrMorioShape.Value
  def locsAndColors: List[(Int, Int, Entity.Colors.Value)]
  def move(dx: Int, dy: Int): Boolean
  def remove(x: Int, y: Int): Option[PillPiece]
}

object Entity {
  object Colors extends Enumeration {
    val Red, Blue, Yellow = Value
  }
}