import java.util.Base64;
import static java.lang.System.out;
import static java.nio.charset.StandardCharsets.ISO_8859_1;

class Base64Java {

    final static int STR_SIZE = 10000000;
    final static int TRIES = 100;

    final static Base64.Decoder dec = Base64.getDecoder();
    final static Base64.Encoder enc = Base64.getEncoder();

    public static void main(String[] args){

        StringBuilder sb = new StringBuilder("");
        for (int i = 0 ; i < STR_SIZE ; i++) {
            sb.append("a");
        }
        final byte[] str = sb.toString().getBytes(ISO_8859_1);
        String str2 = "";
        String str3;

        out.println("JIT warming up");
        for (int i = 0 ; i < 5 ; i++) {
            dec.decode(enc.encodeToString(str));
        }

        out.println("run");
        int s = 0;
        final Long t = System.nanoTime();
        for (int i = 0 ; i < TRIES ; i++) {
            str2 = enc.encodeToString(str);
            s += str2.length();
        }
        out.println("encode: " + s + ", " + (System.nanoTime() - t)/1e9);
        final byte[] encoded = str2.getBytes(ISO_8859_1);

        s = 0;
        final Long t1 = System.nanoTime();
        for (int i = 0 ; i < TRIES ; i++) {
            byte[] b_arr = dec.decode(encoded);
            s += b_arr.length;
        }
        final Long now = System.nanoTime();
        out.println("decode: " + s + ", " + (now - t1) / 1e9);
        out.println("overall time: " + (now - t) / 1e9 + "s");
    }
}
