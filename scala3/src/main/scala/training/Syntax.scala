package training
import training.DataTypes.*
import training.Typeclases.*
import training.Instances.given

object Syntax {
  extension [A: Combinator](aMaybe: Maybe[A])
    def +(bMaybe: Maybe[A]): Maybe[A] = (aMaybe, bMaybe) match
      case (No, No)         => No
      case (Yes(_), No)     => aMaybe
      case (No, Yes(_))     => bMaybe
      case (Yes(a), Yes(b)) => Yes(summon[Combinator[A]].combine(a, b))
}
