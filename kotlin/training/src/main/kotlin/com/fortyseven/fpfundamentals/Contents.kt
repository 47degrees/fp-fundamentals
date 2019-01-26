package com.fortyseven.fpfundamentals

import com.fortyseve.fpfundamentals.Maybe
import com.fortyseven.fpfundamentals.maybe.combinator.combinator

class Program {

  fun getBalanceBank1(): Maybe<Int> = Maybe.Present(100)

  fun getBalanceBank2(): Maybe<Int> = Maybe.Present(80)

  val balance: Maybe<Int>
    get() = Maybe.combinator(Int.combinator())
      .combine(getBalanceBank1(), getBalanceBank2())
}

fun main(args: Array<String>) {
  println(Program().balance)
}
