package drmorio

@remote trait RemoteGrid {
  def upPressed(): Unit
  def upReleased(): Unit
  def downPressed(): Unit
  def downReleased(): Unit
  def leftPressed(): Unit
  def leftReleased(): Unit
  def rightPressed(): Unit
  def rightReleased(): Unit
  def spacePressed(): Unit
  def spaceReleased(): Unit
}