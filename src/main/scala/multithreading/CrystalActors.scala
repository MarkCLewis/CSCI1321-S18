package multithreading

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.WritableImage
import scalafx.scene.image.ImageView
import akka.actor.ActorSystem
import akka.actor.Props

object CrystalActors extends JFXApp {
  val areaWidth = 800
  val areaHeight = 800
  val img = new WritableImage(areaWidth, areaHeight)
  val pr = img.pixelReader.get  // We know that this is safe for a WritableImage
  val pw = img.pixelWriter

  val system = ActorSystem("CrystalGrowth")
  val crystalSuper = system.actorOf(Props(new CrystalSupervisor(areaWidth, areaHeight, pr, pw)), "CrystalSupervisor")
  
  stage = new JFXApp.PrimaryStage {
    title = "Crystal Growth"
    scene = new Scene(areaWidth, areaHeight) {
      val imageView = new ImageView(img)
      content = imageView
    }
  }
}