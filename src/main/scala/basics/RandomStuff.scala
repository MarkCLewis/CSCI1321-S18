package basics

object RandomStuff {
  def remove[A](lst: List[A], isVictim: A => Boolean): List[A] = {
    lst.find(isVictim) match {
      case Some(victim) => lst.filter(_ != victim)
      case None => lst
    }
  }

  def removeFirst[A](lst: List[A], isVictim: A => Boolean): List[A] = {
    lst match {
      case Nil => lst
      case h::t =>
        if(isVictim(h)) t else h::removeFirst(t, isVictim)
    }
  }

  def removeAll[A](lst: List[A], isVictim: A => Boolean): List[A] = {
    lst match {
      case Nil => lst
      case h::t =>
        if(isVictim(h)) removeAll(t, isVictim) else h::removeAll(t, isVictim)
    }
  }
  
  def findAndRemove[A](lst: List[A], isVictim: A => Boolean): (List[A], Option[A]) = {
    lst match {
      case Nil => (lst, None)
      case h::t =>
        if(isVictim(h)) (t, Some(h)) else {
          val (lst2, victim) = findAndRemove(t, isVictim)
          (h::lst2, victim)
        }
    }
  }
}