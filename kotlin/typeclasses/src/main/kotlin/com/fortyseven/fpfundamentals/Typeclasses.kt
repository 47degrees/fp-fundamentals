package com.fortyseven.fpfundamentals

import arrow.Kind

interface Combinator<A> {

  fun combine(x: A, y: A): A
}

interface Transformer<F> {

  fun <A, B> map(fa: Kind<F, A>, f: (A) -> B): Kind<F, B>
}
