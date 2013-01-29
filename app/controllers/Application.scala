package controllers

import play.api._
import play.api.mvc._
import Play.current
import akka.dispatch._
import akka.actor._
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.util.Timeout

object Application extends Controller {

  def sum = {
    val hoge = 1000 * 100000
    println("集計処理が終わりました。結果は" + hoge + "です。");
  }
  def ave = {
    val hoge = 1000 / 10
    println("集計処理が終わりました。平均は" + hoge + "です。");
  }

  def index = Action {
    val callback1 = () => sum
    Sender.startSending(Message.Sum, callback1)
    val callback2 = () => ave
    Sender.startSending(Message.Ave, callback2)
    Ok("非同期処理完了")
  }
}

object Message extends Enumeration {
  type Message = Value
  val Sum, Ave = Value
}

object Sender {
  implicit val system = ActorSystem("RabbitMQSystem")

  def startSending(message: Message.Value, f: () => Any) = {
    val ref = system.actorOf(Props(new SendingActor(f)))
    ref ! message
    system.stop(ref)
  }
}

class SendingActor(f: () => Any) extends Actor {

  def receive = {
    case Message.Sum => {
      Thread.sleep(5000)
      f()
    }
    case Message.Ave => {
      Thread.sleep(3000)
      f()
    }
    case _ => {}
  }
}
