package io.github.aakus
package libs.circe

import io.circe.parser.decode
import io.circe.syntax._
import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.semiauto._

import java.time.Instant
import scala.util.Try

object Codecs {

  val explicit: Unit = {
    val intsJson = List(1, 2, 3).asJson
    // intsJson: io.circe.Json = JArray(
    //   Vector(JNumber(JsonLong(1L)), JNumber(JsonLong(2L)), JNumber(JsonLong(3L)))
    // )

    intsJson.as[List[Int]]
    // res0: io.circe.Decoder.Result[List[Int]] = Right(List(1, 2, 3))

    decode[List[Int]]("[1, 2, 3]")
    // res1: Either[io.circe.Error, List[Int]] = Right(List(1, 2, 3))

  }

  val macroBased: Unit = {
    case class Foo(a: Int, b: String, c: Boolean)

    implicit val fooDecoder: Decoder[Foo] = deriveDecoder
    implicit val fooEncoder: Encoder[Foo] = deriveEncoder

    @JsonCodec case class Bar(i: Int, s: String)

    Bar(13, "Qux").asJson
    // res1: Json = JObject(object[i -> 13,s -> "Qux"])
  }

  val explicitCoders: Unit = {
    case class User(id: Long, firstName: String, lastName: String)

    implicit val decodeUser: Decoder[User] =
      Decoder.forProduct3("id", "first_name", "last_name")(User.apply)

    implicit val encodeUser: Encoder[User] =
      Encoder.forProduct3("id", "first_name", "last_name")(u => (u.id, u.firstName, u.lastName))

  }

  val fullAuto: Unit = {
    import io.circe.generic.auto._, io.circe.syntax._

    case class Person(name: String)
    case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

    Greeting("Hey", Person("Chris"), 3).asJson
    // res0: io.circe.Json = JObject(
    //   object[salutation -> "Hey",person -> {
    //   "name" : "Chris"
    // },exclamationMarks -> 3]
    // )
  }

  val customCoders: Unit = {
    class Thing(val foo: String, val bar: Int)

    implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {
      final def apply(a: Thing): Json = Json.obj(
        ("foo", Json.fromString(a.foo)),
        ("bar", Json.fromInt(a.bar))
      )
    }

    implicit val decodeFoo: Decoder[Thing] = new Decoder[Thing] {
      final def apply(c: HCursor): Decoder.Result[Thing] =
        for {
          foo <- c.downField("foo").as[String]
          bar <- c.downField("bar").as[Int]
        } yield {
          new Thing(foo, bar)
        }
    }

    // or ...
    implicit val encodeInstant: Encoder[Instant] =
      Encoder.encodeString.contramap[Instant](_.toString)

    implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emapTry { str =>
      Try(Instant.parse(str))
    }

  }

  val config: Unit = {

    import io.circe.generic.extras._, io.circe.syntax._

    implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

    @ConfiguredJsonCodec case class User(firstName: String, lastName: String)

    User("Foo", "McBar").asJson
    // res1: Json = JObject(object[first_name -> "Foo",last_name -> "McBar"])

    /** other custom codec configs @see https://circe.github.io/circe/codecs/custom-codecs.html
      */
  }

  val adt: Unit = {

    sealed trait Event

    case class Foo(i: Int) extends Event
    case class Bar(s: String) extends Event
    case class Baz(c: Char) extends Event
    case class Qux(values: List[String]) extends Event

    import cats.syntax.functor._
    import io.circe.{Decoder, Encoder}, io.circe.generic.auto._
    import io.circe.syntax._

    object GenericDerivation {
      implicit val encodeEvent: Encoder[Event] = Encoder.instance {
        case foo @ Foo(_) => foo.asJson
        case bar @ Bar(_) => bar.asJson
        case baz @ Baz(_) => baz.asJson
        case qux @ Qux(_) => qux.asJson
      }

      implicit val decodeEvent: Decoder[Event] =
        List[Decoder[Event]](
          Decoder[Foo].widen,
          Decoder[Bar].widen,
          Decoder[Baz].widen,
          Decoder[Qux].widen
        ).reduceLeft(_ or _)
    }
  }

}
