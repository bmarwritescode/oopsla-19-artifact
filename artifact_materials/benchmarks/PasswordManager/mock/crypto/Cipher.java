public class Cipher {

    private String type;
    private Key key;
    private int mode;
    private boolean[] updated;
    
    public int ENCRYPT_MODE;
    public int DECRYPT_MODE;
    public int UNINITIATED;
    
    public Cipher(String type) {
	this.type = type;
	this.UNINITIATED = 0;
	this.ENCRYPT_MODE = 1;
	this.DECRYPT_MODE = 2;
	this.updated = null;
    }

    public static Cipher getInstance(String type) {
    	return new Cipher(type);
    }

    public static Cipher getInstance(String type,
				     String extra) {
	return new Cipher(type);
    }

    public void init(int opmode,
		     SecretKey key,
		     IvParameterSpec i) {
    	this.key = key;
    	this.mode = opmode;
    }

    public byte[] doFinal(byte[] text) {
	byte[] k = key.getEncoded();
	byte[] result = new byte[text.length];
	if (k.length == 0) {
	    return result;
	} else {
	    if (mode == ENCRYPT_MODE) {
		for (int i = 0; i < text.length; i++) {
		    byte diff = k[i%k.length];
		    byte t = text[i];
		    result[i] = t + diff;
		}
	    } else if (mode == DECRYPT_MODE) {
		for (int i = 0; i < text.length; i++) {
		    byte diff = k[i%k.length];
		    byte t = text[i];
		    result[i] = t - diff;
		}
	    }
	}
	return result;
    }

}
