package io.github.aakus
package libs.jsoniter

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

object Json {

  def simple() = {
    case class Device(id: Int, model: String)
    case class User(name: String, devices: Seq[Device])

    import com.github.plokhotnyuk.jsoniter_scala.macros._
    import com.github.plokhotnyuk.jsoniter_scala.core._

    implicit val codec: JsonValueCodec[User] = JsonCodecMaker.make

    val user = readFromString(
      """{"name":"John","devices":[{"id":1,"model":"HTC One X"}]}"""
    )
    val json = writeToString(
      User(name = "John", devices = Seq(Device(id = 2, model = "iPhone X")))
    )

    println(user)
    println(json)
  }

  def codecs() = {
    sealed abstract class HttpProtocol private (val value: Int)

    case object HttpProtocol {
      val values: Seq[HttpProtocol] = Seq(Http, Https)

      case object Http extends HttpProtocol(0)
      case object Https extends HttpProtocol(1)
    }

    case class Request(url: String, protocol: HttpProtocol)

    implicit val protocolCodec: JsonValueCodec[HttpProtocol] = new JsonValueCodec[HttpProtocol] {
      override def decodeValue(in: JsonReader, default: HttpProtocol): HttpProtocol = {
        val i = in.readInt()
        HttpProtocol.values.find(_.value == i).getOrElse(in.decodeError("illegal protocol"))

      }

      override def encodeValue(x: HttpProtocol, out: JsonWriter): Unit = out.writeVal(x.value)

      override def nullValue: HttpProtocol = null
    }

    implicit val requestCodec: JsonValueCodec[Request] = JsonCodecMaker.make
  }

  def main(args: Array[String]): Unit = {
    simple()
  }

}
