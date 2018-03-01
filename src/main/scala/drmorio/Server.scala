package drmorio

import java.rmi.server.UnicastRemoteObject

case class GameInfo(client1: RemoteClient, grid1: Grid,
    client2: RemoteClient, grid2: Grid)

object Server extends UnicastRemoteObject with App with RemoteServer {
  def connect(client: RemoteClient): RemoteGrid = {
    ???
  }

  private var games = List[GameInfo]()

  var lastTime = 0L
  while (true) {
    val time = System.nanoTime()
    if (lastTime > 0) {
      val delay = (time - lastTime) / 1e9
      grid.update(delay)
    }
    lastTime = time
    for(GameInfo(client1, grid1, client2, grid2) <- games) {
      client1.drawStuff(grid1, grid2, grid1.nextPill)
      client2.drawStuff(grid2, grid1, grid2.nextPill)
    }
  }
}