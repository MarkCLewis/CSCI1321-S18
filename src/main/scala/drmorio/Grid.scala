package drmorio

class Grid {
  // 8 by 16
  private var _currentPill = new Pill(this)
  private var _entities = List.fill(10)(new Virus(util.Random.nextInt(8),
    util.Random.nextInt(16)))

  private var up = false
  private var down = false
  private var left = false
  private var right = false
  private var space = false

  val moveInterval = 0.1
  private var moveDelay = 0.0

  def entities = _entities

  def currentPill = _currentPill

  def update(delay: Double): Unit = {
    moveDelay += delay
    if (moveDelay >= moveInterval) {
      if (left) _currentPill.move(-1, 0)
      if (right) _currentPill.move(1, 0)
      moveDelay = 0.0
    }
    _currentPill.update(delay)
  }

  def upPressed(): Unit = up = true
  def upReleased(): Unit = up = false
  def downPressed(): Unit = down = true
  def downReleased(): Unit = down = false
  def leftPressed(): Unit = left = true
  def leftReleased(): Unit = left = false
  def rightPressed(): Unit = right = true
  def rightReleased(): Unit = right = false
  def spacePressed(): Unit = space = true
  def spaceReleased(): Unit = space = false
  
  def isClear(locations: List[(Int, Int)]): Boolean = {
    println("Checking clear")
    val hits = for((x1, y1) <- locations; e <- entities; (x2, y2) <- e.locations) yield {
      println(x1, y1, x2, y2)
      x1==x2 && y1==y2
    }
    hits.forall(!_)
  }
}