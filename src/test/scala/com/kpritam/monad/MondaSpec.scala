package com.kpritam.monad

import com.kpritam.ArbitraryIntInstances._
import com.kpritam.SimpleCategoryUtils
import com.kpritam.monad.MonadInstances.maybeMonad
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Properties}

import scala.language.higherKinds
import scala.reflect.ClassTag

class MaybeMonadSpec extends MonadSpec(maybeMonad)

abstract class MonadSpec[Box[_]](val monad: Monad[Box])(
    implicit val arbitrary: Arbitrary[Box[Int]],
    tag: ClassTag[Box[_]])
    extends Properties(s"Monad for $tag")
    with MonadProperties[Box]

trait MonadProperties[Box[_]] extends SimpleCategoryUtils {
  self: Properties =>

  val monad: Monad[Box]

  import monad._

  implicit def arbitrary: Arbitrary[Box[A]]

  lazy val toPureF: A ⇒ Box[B] = { a: A =>
    pure(f(a))
  }

  lazy val toPureG: B ⇒ Box[C] = { b: B =>
    pure(g(b))
  }

  // flatMap(pure(a))(f(a)) == f(a)
  property("left identity") = forAll { a: A =>
    flatMap(pure(a))(toPureF) == toPureF(a)
  }

  // flatMap(pure(f(a)))(pure) == pure(f(a))
  property("right identity") = forAll { a: A =>
    flatMap(toPureF(a))(pure) == toPureF(a)
  }

  // flatMap(flatMap(boxA)(boxF))(boxG) == flatMap(boxA)(boxF(_))(boxG)
  property("associativity") = forAll { boxA: Box[A] =>
    flatMap(flatMap(boxA)(toPureF))(toPureG) ==
      flatMap(boxA)(a => flatMap(toPureF(a))(toPureG))
  }
}
