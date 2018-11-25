package training

object typeclasses {

  trait Combinator[A] {
    def combine(x: A, y: A): A
  }


}
