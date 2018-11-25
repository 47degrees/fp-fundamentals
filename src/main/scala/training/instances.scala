package training

import training.datatypes._
import training.typeclasses._

object instances {

  class IntCombinator extends Combinator[Int] {
    override def combine(x: Int, y: Int): Int = x + y
  }

  class MaybeCombinator[A](implicit CA: Combinator[A]) extends Combinator[Maybe[A]] {

    override def combine(x: Maybe[A], y: Maybe[A]): Maybe[A] = (x, y) match {
      case (No, No) => No
      case (No, Yes(yy)) => y
      case (Yes(xx), No) => x
      case (Yes(xx), Yes(yy)) => Yes(CA.combine(xx, yy))
    }

  }

  class MaybeTransformer extends Transformer[Maybe] {
    override def map[A, B](fa: Maybe[A], f: A => B): Maybe[B] = fa match {
      case Yes(a) => Maybe(f(a))
      case No => No
    }
  }


}
