package training

import training.datatypes._
import training.implicits._

object Program extends App {

  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBank1Credentials: Maybe[String] = Yes("MyUser_MyPassword")

  def getBalanceBank1(credentials: String): Maybe[Account] = Yes(Account("a1", 100))

  def getBalanceBank2: Maybe[Int] = Yes(80)

  val moneyInPocket: Int = 20

  val c: Maybe[String] = getBank1Credentials
  val b1: Maybe[Int] = getBank1Credentials.flatMap(cred => getBalanceBank1(cred).map(_.balance))
  val b2: Maybe[Int] = getBalanceBank2
  val p: Maybe[Int] = moneyInPocket.pure

  def balance: Maybe[Int] = b1.combine(b2).combine(p)


  //EDGE OF THE WORLD
  println(balance)

}

