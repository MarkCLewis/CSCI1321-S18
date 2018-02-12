package drmorio

trait Entity {
  def colors: List[Block.Colors.Value]
  def locations: List[(Int, Int)]
  def selfSupporting: Boolean
}