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

  def balance: Maybe[Int] = for {
    c <- getBank1Credentials
    b1 <- getBalanceBank1(c).map(_.balance)
    b2 <- getBalanceBank2
    p <- moneyInPocket.pure
  } yield b1 + b2 + p


  //EDGE OF THE WORLD
  println(balance)

}

