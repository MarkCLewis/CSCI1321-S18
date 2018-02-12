package drmorio

class Virus(x: Int, y: Int) extends Block with Entity {
  val _color = Block.Colors(util.Random.nextInt(3))
  
  def color: Block.Colors.Value = _color

  def colors: List[Block.Colors.Value] = List(color)
  def locations: List[(Int, Int)] = List((x,y))
  def selfSupporting: Boolean = true
}