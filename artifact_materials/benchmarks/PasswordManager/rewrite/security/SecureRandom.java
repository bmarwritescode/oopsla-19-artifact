@rewriteClass
public class SecureRandom {
    @alg
    @constructor
    SecureRandom SecureRandom(Bytes bs);

    @alg
    @pure
    byte[] nextBytes(byte[] k);

    rewrite byte[] nextBytes(Object SecureRandom(Bytes bs),
			     byte[] k) {
	byte[] next = new byte[16];
	return next;
    }
}
