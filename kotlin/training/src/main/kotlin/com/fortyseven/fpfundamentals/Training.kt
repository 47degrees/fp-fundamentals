package com.fortyseven.fpfundamentals

import com.fortyseve.fpfundamentals.Maybe
import com.fortyseve.fpfundamentals.fix
import com.fortyseven.fpfundamentals.maybe.combinator.combinator
import com.fortyseven.fpfundamentals.maybe.transformer.transformer
import com.fortyseven.fpfundamentals.maybe.transformer2.transformer2

data class Account(val id: String, val balance: Int)

data class Statement(val isRich: Boolean, val accounts: Int)

class Program {

  fun getBalanceBank1(): Maybe<Account> = Maybe.Present(Account("a1", 100))

  fun getBalanceBank2(): Maybe<Int> = Maybe.Present(80)

  val b1 = Maybe.transformer().map(getBalanceBank1()) { acc -> acc.balance }
  val b2 = getBalanceBank2()

  val balance: Maybe<Int>
    get() = Maybe.combinator(Int.combinator()).combine(b1, b2)

  val statement: Maybe<Statement>
    get() = Maybe.transformer2().map2(b1, b2) { (x, y) ->
      Statement(isRich = (x + y > 1000), accounts = 2)
    }.fix()
}

fun main(args: Array<String>) {
  val program = Program()
  println(program.balance)
  println(program.statement)
}
