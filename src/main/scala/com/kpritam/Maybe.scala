package com.kpritam

sealed trait Maybe[+A]

final case class Just[A](a: A) extends Maybe[A]

case object Empty extends Maybe[Nothing]
