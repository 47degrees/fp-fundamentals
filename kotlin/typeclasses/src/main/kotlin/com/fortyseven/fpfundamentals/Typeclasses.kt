package com.fortyseven.fpfundamentals

interface Combinator<A> {

  fun combine(x: A, y: A): A
}
