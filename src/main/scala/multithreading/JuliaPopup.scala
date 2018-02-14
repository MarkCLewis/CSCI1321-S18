package multithreading

import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import scalafx.scene.image.PixelWriter
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.concurrent.Futures
import scalafx.application.Platform

class JuliaPopup(c: Complex) {
  val imageWidth = 800
  val imageHeight = 800
  val minReal = -1.5
  val maxReal = 1.5
  val minImag = -1.5
  val maxImag = 1.5

  val img = new WritableImage(800, 800)
  val pixelWriter = img.pixelWriter
  drawJuliaParallel(pixelWriter).foreach( _ => Platform.runLater {
    val stage = new Stage {
      title = "Julia at " + c.r + ", " + c.i
      scene = new Scene(imageWidth, imageHeight) {
        val imageView = new ImageView(img)
        content = imageView
      }
    }

    stage.show()
  })

  def drawJuliaSequential(pw: PixelWriter): Unit = {
    val start = System.nanoTime()
    for (i <- 0 until imageWidth; j <- 0 until imageHeight) {
      val zi = Complex(minReal + i * (maxReal - minReal) / imageWidth, minImag + j * (maxImag - minImag) / imageHeight)
      pw.setColor(i, j, MandelbrotGUI.colorFromIters(juliaIterations(zi)))
    }
    println((System.nanoTime() - start) * 1e-9)
  }

  def drawJuliaParallel(pw: PixelWriter): Future[Unit] = {
    val start = System.nanoTime()
    val futures = for (i <- 0 until imageWidth) yield Future {
      for (j <- 0 until imageHeight) yield {
        val zi = Complex(minReal + i * (maxReal - minReal) / imageWidth, minImag + j * (maxImag - minImag) / imageHeight)
        (i, j, MandelbrotGUI.colorFromIters(juliaIterations(zi)))
      }
    }
    val s = Future.sequence(futures)
    s.map(points => {
      for (row <- points; (i, j, color) <- row) {
        pw.setColor(i, j, color)
      }
      println((System.nanoTime() - start) * 1e-9)
    })
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