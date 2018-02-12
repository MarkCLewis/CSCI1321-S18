package multithreading

import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import scalafx.scene.image.PixelWriter

class JuliaPopup(c: Complex) {
  val imageWidth = 800
  val imageHeight = 800
  val minReal = -1.5
  val maxReal = 1.5
  val minImag = -1.5
  val maxImag = 1.5

  val stage = new Stage {
    title = "Julia at " + c.r + ", " + c.i
    scene = new Scene(imageWidth, imageHeight) {
      val img = new WritableImage(800, 800)
      val imageView = new ImageView(img)
      content = imageView

      val pixelWriter = img.pixelWriter
      drawJuliaSequential(pixelWriter)
    }
  }

  stage.show()

  def drawJuliaSequential(pw: PixelWriter): Unit = {
    for (i <- 0 until imageWidth; j <- 0 until imageHeight) {
      val zi = Complex(minReal + i * (maxReal - minReal) / imageWidth, minImag + j * (maxImag - minImag) / imageHeight)
      pw.setColor(i, j, MandelbrotGUI.colorFromIters(juliaIterations(zi)))
    }
  }

  def juliaIterations(zi: Complex): Int = {
    var z = zi
    var iter = 0
    while (iter < MandelbrotGUI.maxIters && z.mag < 4) {
      z = z * z + c
      iter += 1
    }
    iter
  }
}