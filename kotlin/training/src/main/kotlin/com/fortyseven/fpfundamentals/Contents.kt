package com.fortyseven.fpfundamentals

import com.fortyseve.fpfundamentals.Maybe
import com.fortyseven.fpfundamentals.maybe.combinator.combinator
import com.fortyseven.fpfundamentals.maybe.transformer.transformer

data class Account(val id: String, val balance: Int)

class Program {

  fun getBalanceBank1(): Maybe<Account> = Maybe.Present(Account("a1", 100))

  fun getBalanceBank2(): Maybe<Int> = Maybe.Present(80)

  val balance: Maybe<Int>
    get() = Maybe.combinator(Int.combinator()).combine(
        Maybe.transformer().map(getBalanceBank1()) { acc -> acc.balance },
        getBalanceBank2()
      )
}

fun main(args: Array<String>) {
  println(Program().balance)
}
