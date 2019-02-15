package training

import cats.{Id, Monad}
import cats.implicits._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

class MyProgram[F[_]: Monad] extends App {

  case class Account(id: String, balance: Int)
  case class Statement(isRich: Boolean, accounts: Int)

  def getBank1Credentials: F[String] = "MyUser_MyPassword".pure[F]

  def getBalanceBank1(credentials: String): F[Account] = Account("a1", 100).pure[F]

  def getBalanceBank2: F[Int] = 80.pure[F]

  val moneyInPocket: Int = 20

  def balance: F[Int] = for {
    c <- getBank1Credentials
    b1 <- getBalanceBank1(c).map(_.balance)
    b2 <- getBalanceBank2
    p <- moneyInPocket.pure[F]
  } yield b1 + b2 + p

}

object Program extends App {

  type EitherS[A] = Either[String, A]

  //EDGE OF THE WORLD
  println(new MyProgram[Id].balance)
  println(new MyProgram[EitherS].balance)
  println(new MyProgram[Option].balance)
  println(new MyProgram[Try].balance)
  println(new MyProgram[List].balance)
  println(new MyProgram[Future].balance)

}
