package com.kpritam.functor

import com.kpritam.ArbitraryIntInstances._
import com.kpritam.SimpleCategoryUtils
import com.kpritam.functor.FunctorInstances.maybeFunctor
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Properties}

import scala.language.higherKinds
import scala.reflect.ClassTag

class MaybeFunctorSpec extends FunctorSpec(maybeFunctor)

abstract class FunctorSpec[F[_]](val functor: Functor[F])(
    implicit val arbitrary: Arbitrary[F[Int]],
    tag: ClassTag[F[_]])
    extends Properties(s"Functor for $tag")
    with FunctorProperties[F]

trait FunctorProperties[F[_]] extends SimpleCategoryUtils {
  self: Properties =>

  val functor: Functor[F]

  import functor._

  implicit def arbitrary: Arbitrary[F[A]]

  lazy val mapF: F[A] => F[B] = map(_)(f)
  lazy val mapG: F[B] => F[C] = map(_)(g)
  lazy val mapH: F[C] => F[D] = map(_)(h)

  // map_id == id
  property("identity") = forAll { F: F[A] =>
    map(F)(identity) == F
  }

  // map_(g o f) == (map_g) o (map_f)
  property("composition") = forAll { FA: F[A] =>
    val fG = f andThen g
    val mapFG: F[A] => F[C] = map(_)(fG)
    mapFG(FA) == (mapF andThen mapG)(FA)
  }

  // map_(h o g) o map_f == map_h o map_(g o f)
  property("associativity") = forAll { FA: F[A] =>
    val fG = f andThen g
    val mapFG: F[A] => F[C] = map(_)(fG)
    val gH = g andThen h
    val mapGH: F[B] => F[D] = map(_)(gH)

    (mapF andThen mapGH)(FA) == (mapFG andThen mapH)(FA)
  }
}
