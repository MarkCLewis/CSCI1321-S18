package drmorio

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

object DrMorioShape extends Enumeration {
  val Circle, Square = Value
}

object Renderer {
  val blockSize = 30

  def render(gc: GraphicsContext, grid: Grid): Unit = {
    gc.fill = Color.Black
    gc.fillRect(0, 0, 1000, 1000)
    for (entity <- grid.currentPill :: grid.entities) {
      drawEntity(entity, gc)
    }
    drawEntity(grid.nextPill, gc, 6*blockSize)
  }

  def drawEntity(entity: drmorio.Entity, gc: scalafx.scene.canvas.GraphicsContext, offsetX: Double = 0.0) = {
    val shapeType = entity.shape
    for ((x, y, colorEnum) <- entity.locsAndColors) {
      val color = colorEnum match {
        case Entity.Colors.Red => Color.Red
        case Entity.Colors.Yellow => Color.Yellow
        case Entity.Colors.Blue => Color.Blue
      }
      gc.fill = color
      shapeType match {
        case DrMorioShape.Square =>
          gc.fillRect(x * blockSize + offsetX, y * blockSize,
            blockSize, blockSize)
        case DrMorioShape.Circle =>
          gc.fillOval(x * blockSize + offsetX, y * blockSize,
            blockSize, blockSize)
      }
    }
  }
}