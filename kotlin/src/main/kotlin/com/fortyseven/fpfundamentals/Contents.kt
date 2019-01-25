package com.fortyseven.fpfundamentals

// Data types

sealed class Maybe<A> {
  data class Present<A>(val a: A) : Maybe<A>()
  object Absent : Maybe<Nothing>()
}

// Program

class Program {

  fun getBalanceBank1(): Maybe<Int> = Maybe.Present(100)

  fun getBalanceBank2(): Maybe<Int> = Maybe.Present(80)

  val balance: Int
    get() = getBalanceBank1() + getBalanceBank2()
}

fun main(args: Array<String>) {
  println(Program().balance)
}
