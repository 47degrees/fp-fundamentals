package com.fortyseven.fpfundamentals

import arrow.extension
import arrow.higherkind
import com.fortyseven.fpfundamentals.maybe.combinator.combinator

// Data types

@higherkind
sealed class Maybe<out A> : MaybeOf<A> {
  companion object {
  }

  data class Present<A>(val a: A) : Maybe<A>()
  object Absent : Maybe<Nothing>()

  inline fun <B> fold(ifAbsent: () -> B, ifPresent: (A) -> B): B = when (this) {
    is Absent -> ifAbsent()
    is Present<A> -> ifPresent(a)
  }
}

// Typeclasses

interface Combinator<A> {

  fun combine(x: A, y: A): A
}

// Instances

@extension
interface IntCombinator : Combinator<Int> {
  override fun combine(x: Int, y: Int): Int = x + y
}

fun Int.Companion.combinator(): Combinator<Int> =
  object : IntCombinator {}

@extension
interface MaybeCombinator<A> : Combinator<Maybe<A>> {

  fun CA(): Combinator<A>

  override fun combine(x: Maybe<A>, y: Maybe<A>): Maybe<A> {
    return x.fold(
      { y },
      { xx ->
        y.fold(
          { x },
          { yy -> Maybe.Present(CA().combine(xx, yy)) })
      })
  }
}

// Program

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
