package drmorio

@remote trait RemoteClient {
  def drawStuff(myGrid: PassableGrid, theirGrid: PassableGrid, nextPiece: PillPiece)
}