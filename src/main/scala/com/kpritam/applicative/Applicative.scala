package com.kpritam.applicative

import com.kpritam.functor.Functor

import scala.language.higherKinds

trait Applicative[Box[_]] extends Functor[Box] {
  def pure[A](a: A): Box[A]

  def ap[A, B](boxF: Box[A ⇒ B])(boxA: Box[A]): Box[B]

  override def map[A, B](boxA: Box[A])(f: A ⇒ B): Box[B] = ap(pure(f))(boxA)
}
