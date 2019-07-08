@rewriteClass
class Cipher {

    public static Cipher getInstance(String type) {
	Cipher c = new Cipher(type);
    	return c;
    }

    @alg
    @pure
    String toString(Object txt);
    
    @alg
    @constructor
    Cipher Cipher(String t);

    @alg
    Object init(int mode, Object k, Object iv);
    
    @alg
    @boxedRet
    // byte[] doFinal(Object text);
    Object doFinal(Object text);
    
    // rewrite Object doFinal(Cipher init!(Cipher c1,
    // 					int m1,
    // 					Object k1,
    // 					Object iv1),
    // 			   Object doFinal(Cipher init!(Cipher c2,
    // 						       int m2,
    // 						       Object k2,
    // 						       Object iv2),
    // 					  Object t)) {
    rewrite byte[] doFinal(Cipher init!(Cipher c1,
					int m1,
					Object k1,
					Object iv1),
			   Object doFinal(Cipher init!(Cipher c2,
						       int m2,
						       Object k2,
						       Object iv2),
					  Object t)) {
	if (k1.equals(k2)) {
	    if (m1 == 2) {
		if (m2 == 1) {
		    return t;
		} else {
		    return null;
		}
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }
}
