package training

import training.datatypes._
import training.implicits._

object Program extends App {

  case class Account(id: String, balance: Int)

  def getBalanceBank1: Maybe[Account] = Yes(Account("a1", 100))

  def getBalanceBank2: Maybe[Int] = Yes(80)

  def balance: Maybe[Int] = {

    val b1: Maybe[Int] = getBalanceBank1.map(_.balance)
    val b2: Maybe[Int] = getBalanceBank2

    b1.combine(b2)
  }


  //EDGE OF THE WORLD
  println(balance)

}

