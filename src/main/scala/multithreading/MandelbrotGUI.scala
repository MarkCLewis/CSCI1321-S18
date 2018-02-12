package multithreading

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import scalafx.scene.paint.Color
import scalafx.scene.image.PixelWriter
import scalafx.scene.input.MouseEvent

object MandelbrotGUI extends JFXApp {
  val imageWidth = 800
  val imageHeight = 800
  val minReal = -1.5
  val maxReal = 0.5
  val minImag = -1.0
  val maxImag = 1.0
  val maxIters = 1000
  val logMaxIters = math.log(maxIters)

  stage = new JFXApp.PrimaryStage {
    title = "Mandelbrot Explorer"
    scene = new Scene(800, 800) {
      val img = new WritableImage(800, 800)
      val imageView = new ImageView(img)
      content = imageView

      val pixelWriter = img.pixelWriter
      drawMandelbrotSequential(pixelWriter)

      imageView.requestFocus()
      imageView.onMouseClicked = (me: MouseEvent) => {
        val c = Complex(minReal + me.x * (maxReal - minReal) / imageWidth, minImag + me.y * (maxImag - minImag) / imageHeight)
        new JuliaPopup(c)
      }

    }
  }

  def drawMandelbrotSequential(pw: PixelWriter): Unit = {
    for (i <- 0 until imageWidth; j <- 0 until imageHeight) {
      val c = Complex(minReal + i * (maxReal - minReal) / imageWidth, minImag + j * (maxImag - minImag) / imageHeight)
      pw.setColor(i, j, colorFromIters(mandelbrotIterations(c)))
    }
  }

  def colorFromIters(iters: Int): Color = {
    if (iters == maxIters) Color.Black else {
      val frac = math.log(iters) / logMaxIters
      Color(frac, 0.0, 1.0 - frac, 1.0)
    }
  }

  def mandelbrotIterations(c: Complex): Int = {
    var z = c
    var iter = 0
    while (iter < maxIters && z.mag < 4) {
      z = z * z + c
      iter += 1
    }
    iter
  }
}