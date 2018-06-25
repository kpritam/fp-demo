package com.kpritam.applicative

import com.kpritam.ArbitraryIntInstances._
import com.kpritam.SimpleCategoryUtils
import com.kpritam.applicative.ApplicativeInstances._
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Properties}

import scala.language.higherKinds
import scala.reflect.ClassTag

class MaybeApplicativeSpec extends ApplicativeSpec(maybeApplicative)

abstract class ApplicativeSpec[Box[_]](val applicative: Applicative[Box])(
    implicit val arbitrary: Arbitrary[Box[Int]],
    tag: ClassTag[Box[_]])
    extends Properties(s"Applicative for $tag")
    with ApplicativeProperties[Box]

trait ApplicativeProperties[Box[_]] extends SimpleCategoryUtils {
  self: Properties =>

  val applicative: Applicative[Box]
  import applicative._

  implicit def arbitrary: Arbitrary[Box[A]]

  val pureIdentity: Box[A => A] = pure(identity)
  val pureF: Box[A ⇒ B] = pure(f)
  val toPureA: A ⇒ Box[A] = { a: A =>
    pure(a)
  }

  // ap(id)(a) == a
  property("identity") = forAll { box: Box[A] =>
    ap(pureIdentity)(box) == box
  }

  // ap(pure(f))(pure(a)) == pure(f(a))
  property("homomorphism") = forAll { a: A =>
    ap(pureF)(pure(a)) == pure(f(a))
  }

  // {x => pure(x)}(a) == pure(a)
  property("interchange") = forAll { a: A =>
    toPureA(a) == pure(a)
  }

  // pure(h o g o f) == ap(pure(h o g))(pure(f(a)))
  property("composition") = forAll { a: A =>
    pure((f andThen (g andThen h))(a)) == ap(pure(g andThen h))(pure(f(a)))
  }
}
