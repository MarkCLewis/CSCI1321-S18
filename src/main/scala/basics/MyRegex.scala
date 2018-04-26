package basics

object MyRegex extends App {
  val date = """(\d{1,2})/(\d{1,2})/(\d{4})""".r
  
  val text = """This is some text.
5/31/1875
12/21/1963
This is not a date.
8/1/756"""
  
  for(date(m, d, y) <- text.split("\n")) println(m, d, y)
  
  for(m <- date.findAllMatchIn(text)) println(m)
}