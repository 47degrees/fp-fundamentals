package training
import training.DataTypes.*
import training.Typeclases.*
import training.Instances.given
import training.Syntax.*

@main def hello: Unit =
  case class Account(id: String, balance: Int)
  def getBalanceBank1: Maybe[Account] = Yes(Account("a1", 100))
  def getBalanceBank2: Maybe[Int] = Yes(80)
  val b1: Maybe[Int] = getBalanceBank1.map(_.balance)
  val b2: Maybe[Int] = getBalanceBank2
//   val value: Maybe[Int] = summon[Combinator[Maybe[Int]]].combine(b1, b2)
  val value: Maybe[Int] = b1 + b2

  // EDGE OF THE WORLD
  // Maybe[Int].toString not available, yet
//   println(balance)
