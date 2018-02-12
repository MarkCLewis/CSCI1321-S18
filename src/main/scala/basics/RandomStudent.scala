package basics

object RandomStudent extends App {
	val students = 
		"Quinn Nash Dalton Kaylee Connor Turner Kurt Savannah Callie Marlee Parker Thomas Stanley Chris Pouya Suzette Sarah Emerson Yayo Johnneisha".split(" ")
	println(students(util.Random.nextInt(students.length-1)))
}
