package com.fortyseven.fpfundamentals

import arrow.higherkind

// Data types

@higherkind
sealed class Maybe<out A> : MaybeOf<A> {
  data class Present<A>(val a: A) : Maybe<A>()
  object Absent : Maybe<Nothing>()

  inline fun <B> fold(ifAbsent: () -> B, ifSome: (A) -> B): B = when (this) {
    is Absent -> ifAbsent()
    is Present<A> -> ifSome(a)
  }
}

// Program

class Program {

  fun getBalanceBank1(): Maybe<Int> = Maybe.Present(100)

  fun getBalanceBank2(): Maybe<Int> = Maybe.Present(80)

  val balance: Maybe<Int>
    get() = getBalanceBank1() + getBalanceBank2() // It won't compile
}

fun main(args: Array<String>) {
  println(Program().balance)
}
