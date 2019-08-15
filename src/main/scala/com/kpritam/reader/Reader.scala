package com.kpritam.reader
import com.kpritam.monad.Monad
/*
case class Reader[A, B](run: A => B) {

  def map[C](f: B => C): Reader[A, C] =
    Reader(run andThen f)

  def flatMap[C](f: B => Reader[A, C]): Reader[A, C] =
    Reader(a => f(run(a)).run(a))

  def andThen[C](that: Reader[B, C]): Reader[A, C] =
    Reader(run andThen that.run)

  def compose[C](that: Reader[C, A]): Reader[C, B] =
    Reader(that.run andThen run)
}
 */

object Reader {
  import reader._

  def apply[A, B](run: A => B): Reader[A, B] = ReaderT[Id, A, B](run)

  def ask[A] = Reader[A, A](identity)

  implicit def monadInstance[A]: Monad[Reader[A, ?]] = new Monad[Reader[A, ?]] {
    def pure[B](b: B): Reader[A, B] =
      Reader(_ => b)

    def flatMap[B, C](fa: Reader[A, B])(f: B => Reader[A, C]): Reader[A, C] =
      fa.flatMap(f)
  }

}
