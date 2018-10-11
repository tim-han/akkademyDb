package com.akkademy

import akka.actor.{Actor, Status}
import akka.event.Logging

/**
  * @author tim.han
  */
class PongActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "Ping" =>
      log.info("received Ping.")
      sender() ! "Pong"
    case _ => sender() ! Status.Failure(new Exception("unknown message"))
  }
}
