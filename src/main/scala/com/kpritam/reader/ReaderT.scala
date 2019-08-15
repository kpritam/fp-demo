package com.kpritam.reader
import com.kpritam.monad.Monad
import com.kpritam.functor.Functor
import com.kpritam.applicative.Applicative

// ReaderT = Kleisli
final case class ReaderT[F[_], A, B](run: A => F[B]) {

  def map[C](f: B => C)(implicit F: Functor[F]): ReaderT[F, A, C] =
    ReaderT(a => F.map(run(a))(f))

  def flatMap[C](f: B => ReaderT[F, A, C])(implicit F: Monad[F]): ReaderT[F, A, C] =
    ReaderT { a =>
      F.flatMap(run(a))(b => f(b).run(a))
    }

  def andThen[C](that: ReaderT[F, B, C])(implicit F: Monad[F]): ReaderT[F, A, C] =
    ReaderT { a =>
      F.flatMap(run(a))(b => that.run(b))
    }

  def compose[C](that: ReaderT[F, C, A])(implicit F: Monad[F]): ReaderT[F, C, B] =
    that andThen this
}

object ReaderT {

  def ask[F[_], A](implicit F: Applicative[F]) = ReaderT[F, A, A](F.pure)

  implicit def monadInstance[F[_], A](implicit F: Monad[F]): Monad[ReaderT[F, A, ?]] =
    new Monad[ReaderT[F, A, ?]] {
      def pure[B](b: B): ReaderT[F, A, B] = ReaderT(_ => F.pure(b))

      def flatMap[B, C](fab: ReaderT[F, A, B])(f: B => ReaderT[F, A, C]): ReaderT[F, A, C] =
        fab.flatMap(f)
    }

}
