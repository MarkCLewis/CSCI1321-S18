package drmorio

import java.rmi.server.UnicastRemoteObject

class Grid extends UnicastRemoteObject with RemoteGrid {
  object Mode extends Enumeration {
    val Normal, Falling, Paused = Value
  }
  
  def buildPassable: PassableGrid = {
    val drawEntities = currentPill::entities
    new PassableGrid(drawEntities.flatMap(e => e.locsAndColors.map {
      case (x, y, col) => (x, y, col, e.shape)
    }), _nextPill.locsAndColors.map {case (x, y, col) => (x, y, col, _nextPill.shape) })
  }

  // 8 by 16
  private var _currentPill = new Pill(this)
  private var _nextPill = new Pill(this)
  private var _entities = List.fill[Entity](10)(new Virus(
    util.Random.nextInt(8),
    util.Random.nextInt(13) + 3))
  private var currentMode = Mode.Normal
  private var gridArray = Array.fill[Option[Entity]](16, 8)(None)

  private var up = false
  private var down = false
  private var left = false
  private var right = false
  private var space = false

  val fallInterval = 0.3
  val moveInterval = 0.1
  private var moveDelay = 0.0

  def entities = _entities

  def currentPill = _currentPill

  def nextPill = _nextPill

  def update(delay: Double): Unit = {
    // Fill in the grid array to find things quickly
    for (gy <- gridArray.indices; gx <- gridArray(gy).indices) {
      gridArray(gy)(gx) = _entities.find(e => e.locsAndColors.exists { case (x, y, _) => x == gx && y == gy })
    }
    currentMode match {
      case Mode.Normal =>
        moveDelay += delay
        if (moveDelay >= moveInterval) {
          if (left) currentPill.move(-1, 0)
          if (right) currentPill.move(1, 0)
          if (down) currentPill.move(0, 1)
          if (up) currentPill.flip()
          moveDelay = 0.0
        }
        _currentPill.update(delay)
      case Mode.Falling =>
        moveDelay += delay
        if (moveDelay >= fallInterval) {
          doFalling()
          moveDelay = 0.0
        }
      case Mode.Paused =>
    }

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

  /**
   * This checks if the given locations are currently unoccupied.
   */
  def isClear(locations: List[(Int, Int, _)]): Boolean = {
    locations.forall { case (x, y, _) => x >= 0 && x < 8 && y < 16 && (y < 0 || gridArray(y)(x).isEmpty) }
  }

  /**
   * This should be called by any pill that tries to fall and can't.
   */
  def landPill(p: Pill): Unit = {
    if (p == currentPill) {
      currentPill.locsAndColors.foreach { case (x, y, _) => if (y >= 0) gridArray(y)(x) = Some(currentPill) }
      _entities ::= currentPill
      _currentPill = nextPill
      _nextPill = new Pill(this)
      checkBoard()
    }
  }

  def checkBoard(): Unit = {
    var removed = 0
    var victims = Set[Entity]()
    var replacements = List[PillPiece]()
    // Run down columns looking to remove
    for (x <- gridArray(0).indices) {
      var cnt = 1
      var lastColor: Option[Entity.Colors.Value] = None
      for (y <- gridArray.indices) {
        val thisColor = gridArray(y)(x).flatMap(e => e.locsAndColors.find { case (ex, ey, ec) => x == ex && y == ey }.map(_._3))
        if (thisColor.nonEmpty && thisColor == lastColor) {
          cnt += 1
        } else {
          if (cnt > 3) {
            for (i <- 0 until cnt) {
              val (victim, replacement) = removeElement(x, y - i - 1)
              victims += victim
              replacement.foreach(r => replacements ::= r)
            }
            removed += 1
          }
          cnt = 1
        }
        lastColor = thisColor
      }
      if (cnt > 3) {
        for (i <- 0 until cnt) {
          val (victim, replacement) = removeElement(x, gridArray.length - i - 1)
          victims += victim
          replacement.foreach(r => replacements ::= r)
        }
      }
    }

    // Run across rows looking to remove
    for (y <- gridArray.indices) {
      var cnt = 1
      var lastColor: Option[Entity.Colors.Value] = None
      for (x <- gridArray(y).indices) {
        val thisColor = gridArray(y)(x).flatMap(e => e.locsAndColors.find { case (ex, ey, ec) => x == ex && y == ey }.map(_._3))
        if (thisColor.nonEmpty && thisColor == lastColor) {
          cnt += 1
        } else {
          if (cnt > 3) {
            for (i <- 0 until cnt) {
              val (victim, replacement) = removeElement(x - i - 1, y)
              victims += victim
              replacement.foreach(r => replacements ::= r)
            }
            removed += 1
          }
          cnt = 1
        }
        lastColor = thisColor
      }
      if (cnt > 3) {
        for (i <- 0 until cnt) {
          val (victim, replacement) = removeElement(gridArray(0).length - i - 1, y)
          victims += victim
          replacement.foreach(r => replacements ::= r)
        }
      }
    }

    val uniqueReps = replacements.groupBy(_.parent).filter(_._2.length == 1).values.flatten
    _entities = _entities.filter(!victims.contains(_)) ++ uniqueReps
    if (removed > 0) {
      currentMode = Mode.Falling
    } else {
      currentMode = Mode.Normal
    }
  }

  private def removeElement(x: Int, y: Int): (Entity, Option[PillPiece]) = {
    println(s"Remove at $x $y")
    val e = gridArray(y)(x).get
    val replacement = e.remove(x, y)
    (e, replacement)
  }

  def updateGrid(e: Entity, from: List[(Int, Int, _)], to: List[(Int, Int, _)]): Unit = {
    for ((x, y, _) <- from) gridArray(y)(x) = None
    for ((x, y, _) <- to) gridArray(y)(x) = Some(e)
  }

  def doFalling(): Unit = {
    var dropped = Set[Entity]()
    for (y <- gridArray.indices.reverse; x <- gridArray(y).indices; e <- gridArray(y)(x); if !dropped(e)) {
      val orig = e.locsAndColors
      if (e.move(0, 1)) {
        updateGrid(e, orig, e.locsAndColors)
        dropped += e
      }
    }
    if (dropped.isEmpty) {
      checkBoard()
    }
  }
}