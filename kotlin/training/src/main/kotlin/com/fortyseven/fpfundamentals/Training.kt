package com.fortyseven.fpfundamentals

import arrow.Kind
import arrow.core.Either
import arrow.core.Id
import arrow.core.Option
import arrow.core.Try
import arrow.core.extensions.`try`.monad.monad
import arrow.core.extensions.either.monad.monad
import arrow.core.extensions.id.monad.monad
import arrow.core.extensions.option.monad.monad
import arrow.data.ListK
import arrow.data.extensions.listk.monad.monad
import arrow.typeclasses.Monad

data class Account(val id: String, val balance: Int)

data class Statement(val isRich: Boolean, val accounts: Int)

class Program<F>(private val MF: Monad<F>) {

  private val moneyInPocket: Kind<F, Int> = MF.just(20)
  private val bank1Credentials: Kind<F, String> = MF.just("MyUser_Password")

  private fun getBalanceBank1(credentials: String): Kind<F, Account> =
    MF.just(Account("a1", 100))

  private fun balanceBank2(): Kind<F, Int> = MF.just(80)

  val balance: Kind<F, Int> = MF.binding {
    val credentials = bank1Credentials.bind()
    val b1 = getBalanceBank1(credentials).map { it.balance }.bind()
    val b2 = balanceBank2().bind()
    val p = moneyInPocket.bind()
    b1 + b2 + p
  }
}

fun main(args: Array<String>) {
  println(Program(Id.monad()).balance)
  println(Program(Option.monad()).balance)
  println(Program(Either.monad<String>()).balance)
  println(Program(Try.monad()).balance)
  println(Program(ListK.monad()).balance)
}
