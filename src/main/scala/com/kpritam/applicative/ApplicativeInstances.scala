package com.kpritam.applicative

import com.kpritam.{Empty, Just, Maybe}

object ApplicativeInstances {

  implicit val maybeApplicative: Applicative[Maybe] = new Applicative[Maybe] {
    override def pure[A](a: A): Maybe[A] = Option(a) match {
      case Some(`a`) ⇒ Just(a)
      case None ⇒ Empty
    }

    override def ap[A, B](boxF: Maybe[A ⇒ B])(boxA: Maybe[A]): Maybe[B] =
      (boxF, boxA) match {
        case (Just(f), Just(a)) ⇒ pure(f(a))
        case _ ⇒ Empty
      }
  }

}
