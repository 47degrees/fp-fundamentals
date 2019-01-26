package com.fortyseven.fpfundamentals

import com.fortyseve.fpfundamentals.Maybe
import com.fortyseve.fpfundamentals.fix
import com.fortyseven.fpfundamentals.maybe.combinator.combinator
import com.fortyseven.fpfundamentals.maybe.lifter.lifter
import com.fortyseven.fpfundamentals.maybe.transformer.transformer
import com.fortyseven.fpfundamentals.maybe.transformer2.transformer2

data class Account(val id: String, val balance: Int)

data class Statement(val isRich: Boolean, val accounts: Int)

class Program {

  private val moneyInPocket: Int = 20
  private val getBalanceBank1: Maybe<Account> = Maybe.Present(Account("a1", 100))
  private val getBalanceBank2: Maybe<Int> = Maybe.Present(80)

  private val b1 = Maybe.transformer().map(getBalanceBank1) { acc -> acc.balance }
  private val b2 = getBalanceBank2
  private val p: Maybe<Int> = Maybe.lifter().just(moneyInPocket)

  val balance: Maybe<Int>
    get() {
      val combinator = Maybe.combinator(Int.combinator())
      return combinator.combine(combinator.combine(b1, b2), p)
    }

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
