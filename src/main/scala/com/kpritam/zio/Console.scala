package com.kpritam.zio

sealed trait Console[+A]

final case class Return[A](value: () => A) extends Console[A]
final case class PrintLine[A](line: String, rest: Console[A]) extends Console[A]
final case class ReadLine[A](next: String => Console[A]) extends Console[A]

object Console {

  def interpret[A](program: Console[A]): A =
    program match {
      case PrintLine(line, rest) =>
        println(line)
        interpret(rest)
      case ReadLine(next) =>
        interpret(next(scala.io.StdIn.readLine()))
      case Return(value) =>
        value()
    }

  def succeed[A](a: => A) = Return(() => a)
  def printLine(line: String) = PrintLine(line, succeed(()))
  def readLine = ReadLine(succeed(_))

  implicit class ConsoleSyntax[A](self: Console[A]) {
    def map[B](f: A => B): Console[B] =
      flatMap(a => succeed(f(a)))

    def flatMap[B](f: A => Console[B]): Console[B] =
      self match {
        case Return(value)         => f(value())
        case ReadLine(next)        => ReadLine(line => next(line).flatMap(f))
        case PrintLine(line, rest) => PrintLine(line, rest.flatMap(f))
      }
  }

  val program = for {
    _ <- printLine("What is your name?")
    name <- readLine
    _ <- printLine(s"Hello $name")
  } yield ()

}

object Main extends App {
  import Console._

  val program = for {
    _ <- printLine("What is your name?")
    name <- readLine
    _ <- printLine(s"Hello $name")
  } yield ()

  interpret(program)
}
