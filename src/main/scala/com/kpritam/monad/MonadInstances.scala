package com.kpritam.monad

import com.kpritam.{Empty, Just, Maybe}

object MonadInstances {

  implicit val maybeMonad: Monad[Maybe] = new Monad[Maybe] {
    override def flatMap[A, B](boxA: Maybe[A])(f: A ⇒ Maybe[B]): Maybe[B] =
      boxA match {
        case Just(a) ⇒ f(a)
        case Empty ⇒ Empty
      }

    override def pure[A](a: A): Maybe[A] = Option(a) match {
      case Some(`a`) ⇒ Just(a)
      case None ⇒ Empty
    }
  }

}
