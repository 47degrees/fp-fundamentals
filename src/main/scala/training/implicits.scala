package training

import training.datatypes._
import training.instances._
import training.typeclasses._

object implicits {

  implicit def intCombinator: Combinator[Int] = new IntCombinator

  implicit def maybeCombinator[A](implicit CA: Combinator[A]): Combinator[Maybe[A]] = new MaybeCombinator[A]

  implicit def maybeTransformer: Transformer[Maybe] = new MaybeTransformer

  implicit def maybeTransformer2: Transformer2[Maybe] = new MaybeTransformer2

  implicit def maybeLifter: Lifter[Maybe] = new MaybeLifter

  implicit def maybeFlattener: Flattener[Maybe] = new MaybeFlattener

  //Syntax

  implicit class CombinatorSyntax[A](self: Maybe[A]) {
    def combine(other: Maybe[A])(implicit ev: Combinator[Maybe[A]]): Maybe[A] = ev.combine(self, other)
  }

  implicit class TransformerSyntax[A](self: Maybe[A])(implicit ev: Transformer[Maybe]){
    def map[B](f: A => B): Maybe[B] = ev.map(self, f)
  }

  implicit class LifterSyntax[A](self: A) {
    def pure[F[_]](implicit ev: Lifter[F]): F[A] = ev.pure(self)
  }

  implicit class FlattenerSyntax[A](self: Maybe[A])(implicit ev: Flattener[Maybe]){
    def flatMap[B](f: A => Maybe[B]): Maybe[B] = ev.flatMap(self, f)
  }

}
