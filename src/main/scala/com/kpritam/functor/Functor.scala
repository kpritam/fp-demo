package com.kpritam.functor

import scala.language.higherKinds

trait Functor[Box[_]] {

  def map[A, B](boxA: Box[A])(f: A ⇒ B): Box[B]

}
