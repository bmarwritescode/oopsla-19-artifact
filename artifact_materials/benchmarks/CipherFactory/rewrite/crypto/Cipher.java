@rewriteClass
class Cipher {

    public static Cipher getInstance(String type) {
    	return new Cipher(type);
    }

    @alg
    @pure
    String toString(Object str);

    @alg
    @pure
    @boxedRet
    byte[] readEncoded(String pt);
    
    @alg
    @constructor
    Cipher Cipher(String t);

    @alg
    Object init(int mode, SecretKeySpec s);
    
    @alg
    @boxedRet
    Object doFinal(Object text);
    
    rewrite Object doFinal(Cipher init!(Cipher c1, int m1, SecretKeySpec k1), Object readEncoded(Cipher c3, Object toString(Cipher c4, Object doFinal(Object init!(Cipher c2, int m2, SecretKeySpec k2), Object t)))) {
    	return k1.equals(k2) ? ((m1 == 2 && m2 == 1) ? t : null) : null;
    }
}
