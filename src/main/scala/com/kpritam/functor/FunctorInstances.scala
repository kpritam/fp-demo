package com.kpritam.functor

import simulacrum._
import com.kpritam.{Empty, Just, Maybe}

object FunctorInstances {

  implicit val maybeFunctor: Functor[Maybe] = new Functor[Maybe] {
    override def map[A, B](fa: Maybe[A])(f: A ⇒ B): Maybe[B] = fa match {
      case Just(a) ⇒ Just(f(a))
      case Empty ⇒ Empty
    }
  }

  implicit def function1Functor[X]: Functor[X => ?] = new Functor[X => ?] {
    override def map[A, B](f: X => A)(g: A ⇒ B): X => B = f andThen g
  }

}
