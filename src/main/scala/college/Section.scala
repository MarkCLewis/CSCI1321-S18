package college

class Section(val course: Course, capacity: Int) {
  private var students: List[Student] = Nil
  
  def addStudent(s: Student): Boolean = {
    if(students.length < capacity) {
      // TODO - add student
      true
    } else false
  }
}