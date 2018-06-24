package com.kpritam.functor

import com.kpritam.{Empty, Just, Maybe}

object FunctorInstances {

  implicit val maybeFunctor: Functor[Maybe] = new Functor[Maybe] {
    override def map[A, B](boxA: Maybe[A])(f: A ⇒ B): Maybe[B] = boxA match {
      case Just(a) ⇒ Just(f(a))
      case Empty ⇒ Empty
    }
  }

}
