package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.duration._
/**
  * @author tim.han
  */
class MessageBoxTest extends FunSpecLike with Matchers {
  implicit val system = ActorSystem()
  implicit val timeout = Timeout( 5 seconds )

  describe("messageBox") {
    describe("given string") {
      it("should store message") {
        val actorRef = TestActorRef(new MessageBox)
        actorRef ! "message1"
        actorRef ! "message2"

        val messageBox = actorRef.underlyingActor
        messageBox.messages.toList equals List("message1", "message2")
      }
    }
  }
}
