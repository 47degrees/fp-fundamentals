package training

object datatypes {

  sealed trait Maybe[+A]
  case class Yes[A](a: A) extends Maybe[A]
  case object No extends Maybe[Nothing]

}
