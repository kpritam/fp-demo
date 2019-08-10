package com.kpritam.applicative

import com.kpritam.functor.Functor

import scala.language.higherKinds

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]

  def ap[A, B](ff: F[A ⇒ B])(fa: F[A]): F[B]

  override def map[A, B](fa: F[A])(f: A ⇒ B): F[B] = ap(pure(f))(fa)

  def map2[A, B, Z](fa: F[A], fb: F[B])(f: (A, B) => Z): F[Z] =
    ap(fa)(map(fb)(b => f(_, b)))

  def tuple2[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    map2(fa, fb)((_, _))
}
