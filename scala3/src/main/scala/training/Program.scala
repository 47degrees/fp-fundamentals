package training
import training.DataTypes.*

@main def hello: Unit =
  def getBalanceBank1: Maybe[Int] = Yes(100)
  def getBalanceBank2: Maybe[Int] = Yes(80)
  def balance: Int =
    val b1: Maybe[Int] = getBalanceBank1
    val b2: Maybe[Int] = getBalanceBank2
    b1 + b2 // it will fail
  // EDGE OF THE WORLD
  println(balance)
