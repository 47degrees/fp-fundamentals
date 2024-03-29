package training

import training.DataTypes.*
import training.Typeclases.*

object Instances:
  given Combinator[Int] with
    override def combine(x: Int, y: Int): Int = x + y

  given [A: Combinator]: Combinator[Maybe[A]] with
    override def combine(x: Maybe[A], y: Maybe[A]): Maybe[A] = (x, y) match
      case (No, No)           => No
      case (No, _)            => y
      case (_, No)            => x
      case (Yes(xx), Yes(yy)) => Yes(summon[Combinator[A]].combine(xx, yy))

  given Transformer[Maybe] with
    override def map[A, B](fa: Maybe[A], f: A => B): Maybe[B] = fa match
      case Yes(a) => Maybe(f(a))
      case No     => No

  given Transformer2[Maybe] with
    override def ap[A, B](fa: Maybe[A], ff: Maybe[A => B]): Maybe[B] =
      (ff, fa) match
        case (Yes(f), Yes(a)) => Maybe(f(a))
        case (_, _)           => No
    override def map[A, B](fa: Maybe[A], f: A => B): Maybe[B] = fa match
      case Yes(a) => Maybe(f(a))
      case No     => No

  given Lifter[Maybe] with
    override def pure[A](a: A): Maybe[A] = Maybe(a)

  given Flattener[Maybe] with
    override def flatMap[A, B](fa: Maybe[A], f: A => Maybe[B]): Maybe[B] = fa match
      case Yes(a) => f(a)
      case No     => No

end Instances
