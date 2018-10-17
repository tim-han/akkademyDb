package com.akkademy

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * @author tim.han
  */
class AskExampleTest extends FunSpecLike with Matchers {
  val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)
  val pongActor: ActorRef = system.actorOf(Props[PongActor])
  describe("Pong actor") {
    it("should respond with Pong") {
      val future = pongActor ? "Ping"
      val result = Await.result(future.mapTo[String], 1 second)
      assert(result == "Pong")
    }
  }
  it("should fail on unknown message") {
    val future = pongActor ? "unknown"
    intercept[Exception] {
      Await.result(future.mapTo[String], 1 second)
    }
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  describe("FutureExamples") {
    it("should print to console 'Pong'") {
      askPong("Ping").onSuccess {
        case msg: String => println("replied with: " + msg)
      }
      Thread.sleep(100)
    }
  }

  describe("Future to Future") {
    it("should print to console 'Pong'") {
      val f: Future[String] = askPong("Ping").flatMap(msg => askPong("Ping"))

      f.onSuccess {
        case msg: String => println(msg)
      }
    }
  }

  describe("Handling failure just by onFailure") {
    it("should print to console unknown message") {
      askPong("causeError").onFailure {
        case e: Exception => println(e.getMessage)
      }
    }
  }

  describe("Handling failure by recover") {
    it("should print to console default") {
      val f = askPong("causeError").recover {
        case t: Exception => println("default")
      }
    }
  }

  describe("Handling failure by recoverWith(retrying)") {
    it("should print to console Pong") {
      askPong("causeError").recoverWith {
        case t: Exception => askPong("Ping")
      }.onSuccess {
        case msg: String => println(msg)
      }
    }
  }

  describe("Chaining futures with handling exception") {
    it("should print to console Pong") {
      askPong("Ping")
        .flatMap(x => askPong("Ping" + x))
        .recover({ case t: Exception => "There was an error"} )
    }
  }

  describe("Handling list of future") {
    it("should print to ...") {
      val listOfFutures: List[Future[String]] =
        List("Pong", "Pong", "failed").map(x => askPong(x).recover { case t: Exception => ""} )

      val futureOfList: Future[List[String]] = Future.sequence(listOfFutures)
    }
  }




  private def askPong(msg: String): Future[String] =
    (pongActor ? msg).mapTo[String]
}
