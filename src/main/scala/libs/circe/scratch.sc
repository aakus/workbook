import io.circe.{HCursor, Json}
import io.circe.parser.parse
val json: String = """
  {
    "id": "c730433b-082c-4984-9d66-855c243266f0",
    "name": "Foo",
    "counts": [1, 2, 3],
    "values": {
      "bar": true,
      "baz": 100.001,
      "qux": ["a", "b"]
    }
  }
  """

val doc: Json = parse(json).getOrElse(Json.Null)

val cursor: HCursor = doc.hcursor
