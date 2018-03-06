package drmorio

@remote trait RemoteClient {
  def drawGrids(myGrid: PassableGrid, theirGrid: PassableGrid): Unit
  def renderMessage(msg: String): Unit
}