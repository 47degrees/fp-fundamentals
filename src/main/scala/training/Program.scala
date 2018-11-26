package training

import training.datatypes._
import training.implicits._

object Program extends App {

  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBalanceBank1: Maybe[Account] = Yes(Account("a1", 100))

  def getBalanceBank2: Maybe[Int] = Yes(80)

  val moneyInPocket: Int = 20

  val b1: Maybe[Int] = getBalanceBank1.map(_.balance)
  val b2: Maybe[Int] = getBalanceBank2
  val p: Maybe[Int] = moneyInPocket.pure

  def balance: Maybe[Int] = b1.combine(b2).combine(p)


  //EDGE OF THE WORLD
  println(balance)

}

