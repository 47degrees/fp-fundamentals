package training

object typeclasses {

  trait Combinator[A] {
    def combine(x: A, y: A): A
  }

  trait Transformer[F[_]] {
    def map[A, B](fa: F[A], f: A => B): F[B]
  }


}
