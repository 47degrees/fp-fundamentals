package com.fortyseven.fpfundamentals

class Program {

  fun getBalanceBank1(): Int = 100

  fun getBalanceBank2(): Int = 80

  val balance: Int
    get() = getBalanceBank1() + getBalanceBank2()
}

fun main(args: Array<String>) {
  println(Program().balance)
}
