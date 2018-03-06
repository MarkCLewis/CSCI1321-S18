package drmorio

import java.rmi.server.UnicastRemoteObject

case class GameInfo(client1: RemoteClient, grid1: Grid,
  client2: RemoteClient, grid2: Grid)

object Server extends UnicastRemoteObject with App with RemoteServer {
  java.rmi.registry.LocateRegistry.createRegistry(1099)

  java.rmi.Naming.rebind("DrMorioServer", this)

  private var waiting: Option[(RemoteClient, Grid)] = None
  def connect(client: RemoteClient): RemoteGrid = {
    val grid = new Grid
    waiting match {
      case Some((c, g)) =>
        games ::= GameInfo(c, g, client, grid)
        waiting = None
      case None =>
        waiting = Some(client, grid)
    }
    grid
  }

  private var games = List[GameInfo]()

  var lastTime = 0L
  while (true) {
    val time = System.nanoTime()
    if (lastTime > 0) {
      val delay = (time - lastTime) / 1e9
      for (gi <- games) {
        gi.grid1.update(delay)
        gi.grid2.update(delay)
      }
      for (GameInfo(client1, grid1, client2, grid2) <- games) {
        val pg1 = grid1.buildPassable
        val pg2 = grid2.buildPassable
        client1.drawGrids(pg1, pg2)
        client2.drawGrids(pg2, pg1)
      }
      waiting.foreach { case (client, _) => client.renderMessage("Waiting for partner.")}
    }
    lastTime = time
  }
}