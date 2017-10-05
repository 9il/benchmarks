/+dub.json:
{
    "name": "json_d_asdf",
    "dependencies": {
        "asdf": {"path" : "../../asdf"}
    },
    "dflags-ldc": ["-mcpu=native"]
}
+/
import std.stdio;
import std.file;
import asdf;

struct Coord { double x = 0, y = 0, z = 0; }

struct CoordAvg
{
    size_t length;
    Coord coord;

    void put()(Coord val)
    {
        length++;
        coord.x += val.x;
        coord.y += val.y;
        coord.z += val.z;
    }

    Coord avg()
    {
        with(coord) return Coord(x / length, y / length, z / length);
    }
}

struct Avg
{
    // http://docs.asdf.dlang.io/asdf_serialization.html#.serializationLikeArray
	@serializationLikeArray
	@serializedAs!Coord // input element type of
	@serializationIgnoreOut
    CoordAvg coordinates; //`put` method is used
}

void main()
{
    auto avgCoord = File("1.json")
        .byChunk(10000)
        .parseJson()
        .deserialize!Avg
        .coordinates
        .avg;

    with(avgCoord) printf("%.8f\n%.8f\n%.8f\n", x, y, z);
}
