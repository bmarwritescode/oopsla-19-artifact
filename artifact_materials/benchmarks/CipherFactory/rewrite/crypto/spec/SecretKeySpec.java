@rewriteClass
public class SecretKeySpec implements Key {
    @alg
    @pure
    byte[] getEncoded();

    @alg
    @pure
    boolean equals(Object s);

    rewrite Object equals(Object SecretKeySpec(), Object s2) {
    	return true;
    }

    rewrite Object getEncoded(Object SecretKeySpec()) {
    	return new byte[16];
    }
        
}
