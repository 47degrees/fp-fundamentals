package training
import training.DataTypes.*
import training.Typeclases.*
import training.Instances.given

@main def hello: Unit =
  def getBalanceBank1: Maybe[Int] = Yes(100)
  def getBalanceBank2: Maybe[Int] = Yes(80)
  val b1: Maybe[Int] = getBalanceBank1
  val b2: Maybe[Int] = getBalanceBank2
  val value: Maybe[Int] = summon[Combinator[Maybe[Int]]].combine(b1, b2)

  // EDGE OF THE WORLD
  // Maybe[Int].toString not available, yet
//   println(balance)
