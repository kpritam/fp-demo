package com.kpritam.monad

import com.kpritam.applicative.Applicative

import scala.language.higherKinds

trait Monad[F[_]] extends Applicative[F] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def flatten[A](ffa: F[F[A]]): F[A] = flatMap(ffa)(identity)

  override def ap[A, B](fab: F[A => B])(fa: F[A]): F[B] =
    flatMap(fab)(map(fa))

  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a ⇒ pure(f(a)))

}
