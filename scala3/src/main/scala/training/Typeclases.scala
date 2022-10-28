package training

object Typeclases:
  trait Combinator[A]:
    def combine(x: A, y: A): A

  trait Transformer[F[_]]:
    def map[A, B](fa: F[A], f: A => B): F[B]

  trait Transformer2[F[_]] extends Transformer[F]:
    def ap[A, B](fa: F[A], ff: F[A => B]): F[B]
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
      ap(fb, map(fa, (a: A) => (b: B) => (a, b)))
    def map2[A, B, Z](fa: F[A], fb: F[B], f: (A, B) => Z): F[Z] =
      map(product(fa, fb), f.tupled)

  trait Lifter[F[_]]:
    def pure[A](a: A): F[A]

  trait Flattener[F[_]]:
    def flatMap[A, B](fa: F[A], f: A => F[B]): F[B]
