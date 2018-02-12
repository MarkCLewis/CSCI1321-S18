package drmorio

trait Block {
  def color: Block.Colors.Value
}

object Block {
  object Colors extends Enumeration {
    val Red, Blue, Yellow = Value
  }
}