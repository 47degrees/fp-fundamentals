package training

import training.datatypes._
import training.implicits._

object Program extends App {

  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBalanceBank1: Maybe[Account] = Yes(Account("a1", 100))

  def getBalanceBank2: Maybe[Int] = Yes(80)

  val b1: Maybe[Int] = getBalanceBank1.map(_.balance)
  val b2: Maybe[Int] = getBalanceBank2

  def balance: Maybe[Int] = b1.combine(b2)

  def statement: Maybe[Statement] =
    maybeTransformer2.map2(b1, b2, {(a: Int, b: Int) => Statement(a + b > 1000, 2) })



  //EDGE OF THE WORLD
  println(balance)
  println(statement)

}

