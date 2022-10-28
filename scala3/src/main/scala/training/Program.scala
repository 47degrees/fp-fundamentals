package training
import training.DataTypes.*
import training.Typeclases.*
import training.Instances.given
import training.Syntax.*

@main def hello: Unit =
  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBalanceBank1: Maybe[Account] = Yes(Account("a1", 100))
  def getBalanceBank2: Maybe[Int] = Yes(80)
  val b1: Maybe[Int] = getBalanceBank1.map(_.balance)
  val b2: Maybe[Int] = getBalanceBank2
  val balance: Maybe[Int] = b1 + b2
  def statement =
    summon[Transformer2[Maybe]].map2(b1, b2, (x: Int, y: Int) => Statement(x + y > 1000, 2))

  // EDGE OF THE WORLD
  println(balance)
  println(statement)
