package com.kpritam.reader
import com.kpritam.monad.Monad

package object reader {

  type Id[A] = A
  implicit def idMonad: Monad[Id] = new Monad[Id] {
    def pure[A](a: A): Id[A] = a
    def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)
  }

  type Reader[A, B] = ReaderT[Id, A, B]

}
