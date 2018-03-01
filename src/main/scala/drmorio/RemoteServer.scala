package drmorio

@remote trait RemoteServer {
  def connect(client: RemoteClient): RemoteGrid
}