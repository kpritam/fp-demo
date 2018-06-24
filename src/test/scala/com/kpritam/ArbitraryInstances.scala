package com.kpritam

import org.scalacheck.{Arbitrary, Gen}

object ArbitraryIntInstances extends ArbitraryInstances[Int]

trait ArbitraryInstances[T] {

  implicit def arbitraryMaybe(
      implicit arbitrary: Arbitrary[T]): Arbitrary[Maybe[T]] = Arbitrary {
    Gen.option(arbitrary.arbitrary).map {
      case Some(t) => Just(t)
      case None    => Empty
    }
  }

}
