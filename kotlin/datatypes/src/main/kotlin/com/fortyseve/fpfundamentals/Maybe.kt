package com.fortyseve.fpfundamentals

import arrow.higherkind

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

  fun <B> map(f: (A) -> B): Maybe<B> = when (this) {
    is Absent -> Absent
    is Present<A> -> Present(f(a))
  }
}
