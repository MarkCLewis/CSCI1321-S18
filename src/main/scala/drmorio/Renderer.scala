package drmorio

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

object Renderer {
  val blockSize = 30

  def render(gc: GraphicsContext, grid: Grid): Unit = {
    gc.fill = Color.Black
    gc.fillRect(0, 0, 1000, 1000)
    for (entity <- grid.currentPill :: grid.entities) {
      for ((colorEnum, loc) <- entity.colors.zip(entity.locations)) {
        val color = colorEnum match {
          case Block.Colors.Red => Color.Red
          case Block.Colors.Yellow => Color.Yellow
          case Block.Colors.Blue => Color.Blue
        }
        gc.fill = color
        gc.fillRect(loc._1 * blockSize, loc._2 * blockSize,
          blockSize, blockSize)
      }
    }
  }
}