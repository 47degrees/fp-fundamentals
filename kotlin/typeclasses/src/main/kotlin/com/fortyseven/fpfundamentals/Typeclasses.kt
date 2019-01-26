package com.fortyseven.fpfundamentals

import arrow.Kind
import arrow.core.Tuple2

interface Combinator<A> {

  fun combine(x: A, y: A): A
}

interface Transformer<F> {

  fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
}

interface Transformer2<F> : Transformer<F> {

  fun <A, B> ap(fa: Kind<F, A>, ff: Kind<F, (A) -> B>): Kind<F, B>

  fun <A, B> product(fa: Kind<F, A>, fb: Kind<F, B>): Kind<F, Tuple2<A, B>> =
    ap(fb, map(fa) { a: A -> { b: B -> Tuple2(a, b) } })

  fun <A, B, Z> map2(fa: Kind<F, A>, fb: Kind<F, B>, f: (Tuple2<A, B>) -> Z): Kind<F, Z> =
    map(product(fa, fb), f)
}

interface Lifter<F> {

  fun <A> just(a: A): Kind<F, A>
}
