package training

import cats.implicits.given

@main def hello: Unit =
  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBank1Credentials = Some("MyUser_MyPassword")
  def getBalanceBank1(credentials: String) = Some(Account("a1", 100))
  def getBalanceBank2 = Some(80)
  val moneyInPocket: Int = 20

  val balance =
    for
      c <- getBank1Credentials
      b1 <- getBalanceBank1(c).map(_.balance)
      b2 <- getBalanceBank2
      p <- moneyInPocket.pure[Option]
    yield b1 + b2 + p

  // EDGE OF THE WORLD
  println(balance)
