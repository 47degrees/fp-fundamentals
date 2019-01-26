package com.fortyseven.fpfundamentals

import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.option.applicative.applicative
import arrow.core.extensions.option.functor.functor
import arrow.core.extensions.option.monad.monad
import arrow.core.extensions.option.semigroup.semigroup
import arrow.core.extensions.semigroup
import arrow.core.fix

data class Account(val id: String, val balance: Int)

data class Statement(val isRich: Boolean, val accounts: Int)

class Program {

  private val moneyInPocket: Int = 20
  private val bank1Credentials: Option<String> = Some("MyUser_Password")

  private fun getBalanceBank1(credentials: String): Option<Account> {
    return Some(Account("a1", 100))
  }

  private val balanceBank2: Option<Int> = Some(80)

  private val b1 = Option.monad().run {
    bank1Credentials.flatMap { credentials ->
      Option.functor().run { getBalanceBank1(credentials).map { it.balance } }
    }
  }
  private val b2 = balanceBank2
  private val p: Option<Int> = Option.applicative().just(moneyInPocket)

  val balance: Option<Int>
    get() {
      val combinator = Option.semigroup(Int.semigroup())
      return combinator.run { b1.combine(b2).combine(p) }
    }

  val statement: Option<Statement>
    get() = Option.applicative().run {
      b1.map2(b2) { (x, y) ->
        Statement(isRich = (x + y > 1000), accounts = 2)
      }.fix()
    }
}

fun main(args: Array<String>) {
  val program = Program()
  println(program.balance)
  println(program.statement)
}
