package training

import training.DataTypes.*
import training.Typeclases.*

object Instances {
  given Combinator[Int] with
    override def combine(x: Int, y: Int): Int = x + y

  given [A: Combinator]: Combinator[Maybe[A]] with
    override def combine(x: Maybe[A], y: Maybe[A]): Maybe[A] = (x, y) match {
      case (No, No)           => No
      case (No, _)            => y
      case (_, No)            => x
      case (Yes(xx), Yes(yy)) => Yes(summon[Combinator[A]].combine(xx, yy))
    }
}
