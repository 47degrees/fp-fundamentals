package training

object Typeclases:
  trait Combinator[A]:
    def combine(x: A, y: A): A
