package training
import training.DataTypes.*
import training.Typeclases.*
import training.Instances.given
import training.Syntax.*

@main def hello: Unit =
  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBank1Credentials: Maybe[String] = Yes("MyUser_MyPassword")
  def getBalanceBank1(credentials: String): Maybe[Account] = Yes(Account("a1", 100))
  def getBalanceBank2: Maybe[Int] = Yes(80)
  val moneyInPocket: Int = 20

  val b1: Maybe[Int] = getBank1Credentials.flatMap(cred => getBalanceBank1(cred).map(_.balance))
  val b2: Maybe[Int] = getBalanceBank2
  val p: Maybe[Int] = moneyInPocket.pure
  val balance: Maybe[Int] = b1 + b2 + p

  // EDGE OF THE WORLD
  println(balance)
