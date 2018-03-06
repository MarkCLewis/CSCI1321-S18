package drmorio

case class PassableGrid(locsAndColors: Seq[(Int, Int, Entity.Colors.Value, DrMorioShape.Value)],
    nextPill: Seq[(Int, Int, Entity.Colors.Value, DrMorioShape.Value)])