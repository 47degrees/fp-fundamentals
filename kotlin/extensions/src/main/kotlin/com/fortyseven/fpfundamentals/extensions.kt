package com.fortyseven.fpfundamentals

import arrow.Kind
import arrow.extension
import com.fortyseve.fpfundamentals.ForMaybe
import com.fortyseve.fpfundamentals.Maybe
import com.fortyseve.fpfundamentals.fix

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

@extension
interface MaybeTransformer : Transformer<ForMaybe> {

  override fun <A, B> map(fa: Kind<ForMaybe, A>, f: (A) -> B): Maybe<B> =
    fa.fix().map(f)
}
