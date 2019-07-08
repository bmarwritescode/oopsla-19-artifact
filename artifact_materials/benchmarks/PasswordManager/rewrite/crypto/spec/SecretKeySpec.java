@rewriteClass
public class SecretKeySpec implements Key {
    @alg
    @pure
    byte[] getEncoded();

    rewrite Object getEncoded(Object SecretKeySpec()) {
	byte[] encoded = new byte[16];
    	return encoded;
    }    
}
