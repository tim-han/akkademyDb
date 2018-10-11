package com.akkademy

import akka.actor.Actor
import akka.event.Logging

import scala.collection.mutable

/**
  * @author tim.han
  */
class MessageBox extends Actor {
  val messages = mutable.ListBuffer[String]()
  val log = Logging(context.system, this)

  override def receive = {
    case msg: String =>
      messages += msg
      log.info(s"received message: ${msg}. current messages: ${messages}")
    case other => log.info("received unknown type message.")
  }
}
