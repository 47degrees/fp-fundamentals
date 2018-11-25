package training

import training.datatypes._

object Program extends App {

  def getBalanceBank1: Maybe[Int] = Yes(100)

  def getBalanceBank2: Maybe[Int] = Yes(80)

  def balance: Maybe[Int] = {

    val b1: Maybe[Int] = getBalanceBank1
    val b2: Maybe[Int] = getBalanceBank2

    b1 + b2 //It will fail
  }


  //EDGE OF THE WORLD
  println(balance)

}

