import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Formatter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * functionality of encription and decreption
 * 
 * @author azmy
 * 
 */
public class Cryptographer {

	public SecretKey GCMSecretKey; // AES/GCM key
	public SecretKeySpec Hash_secret_key; // HMAC secret key
	private Mac sha256_HMAC;
	private Cipher myCypherOut;
	byte[] randomIv;
	IvParameterSpec ivParameterSpec;
	private int MAX_LENGTH_PASSWORD;
    
        public Cryptographer() {
	    Object[] localObjs = new Object[2];

	    stmts(localObjs);	    
	}

    	public String hash(String plainTxt) {
    	    return sha256_HMAC.doFinal(plainTxt);
    	}
    
    generator Mac genMac(Object[] localObjs) {
	if (??) { return sha256_HMAC; }
	if (??) { return new Mac(); }
	return null;
    }
    
    generator SecretKeySpec genSecretKeySpec(Object[] localObjs) {
	if (??) { return GCMSecretKey; }
	if (??) {
	    byte[] bs2 = genBytes(localObjs);
	    Bytes bs = new Bytes(bs2);
	    return new SecretKeySpec(bs);
	}
	return null;
    }

    generator IvParameterSpec genIvParameterSpec(Object[] localObjs) {
	if (??) { return ivParameterSpec; }
	if (??) {
	    byte[] bs = genBytes(localObjs);
	    return new IvParameterSpec(bs);
	}
	return null;
    }
    
    generator byte[] genBytes(Object[] localObjs) {
	if (??) {
	    Bytes bytes = (Bytes) localObjs[1];
	    return bytes.toArray();
	}
	if (??) {
	    String plainText = genString(localObjs);
	    Cipher c = genCipher(localObjs);
	    @unbox
	    byte[] answer = c.doFinal(plainText);
	    return answer;		
	} if (??) {
	    SecureRandom random = new SecureRandom();
	    byte[] randomKey = new byte[16];
	    random.nextBytes(randomKey);
	    return randomKey;
	}
	return new byte[16];
    }

    generator Cipher genCipher(Object[] localObjs) {
	if (??) { return myCypherOut; }
	if (??) { return new Cipher(); }
	return null;
    }
    
    generator String genString(Object[] localObjs) {
	if (??) { return (String) localObjs[0]; }
	if (??) {
	    String plainText = genString(localObjs);
	    @box
	    byte[] plainText_bytes = plainText.getBytes();
	    Cipher c = genCipher(localObjs);
	    @isBoxed
	    byte[] cipherText = c.doFinal(plainText_bytes);
	    return cipherText;
	}
	if (??) {
	    byte[] bs = genBytes(localObjs);
	    return new String(bs);
	}
	return null;
    }
    
    generator int genInt(Object[] localObjs) {
	return {| MAX_LENGTH_PASSWORD, ?? |};
    }
    
    generator void voidFuncs(Object[] localObjs) {
	if (??) {
	    Cipher c = genCipher(localObjs);
	    int i = genInt(localObjs);
	    SecretKeySpec k = genSecretKeySpec(localObjs);
	    IvParameterSpec iv = genIvParameterSpec(localObjs);
	    c.init(i, k, iv);
	}
	if (??) {
	    Mac m = genMac(localObjs);
	    SecretKeySpec s = genSecretKeySpec(localObjs);
	    m.init(s);
	}
    }
    
    generator void stmts(Object[] localObjs) {
	if (??) { voidFuncs(localObjs); }
	if (??) { localObjs[0] = genString(localObjs); }
	if (??) {
	    byte[] bs = genBytes(localObjs);
	    localObjs[1] = new Bytes(bs);
	}	
	if (??) { myCypherOut = genCipher(localObjs); }
	if (??) { GCMSecretKey = genSecretKeySpec(localObjs); }
	if (??) { ivParameterSpec = genIvParameterSpec(localObjs); }
	if (??) { sha256_HMAC = genMac(localObjs); }
	if (??) { MAX_LENGTH_PASSWORD = genInt(localObjs); }
	if (??) { stmts(localObjs); }
    }
    
    public String encrypt(String plainText) {
	Object[] localObjs = new Object[2];
	localObjs[0] = plainText;

	stmts(localObjs);
	    
	return genString(localObjs);
    }
    
    public String decrypt(String plainText) {
	Object[] localObjs = new Object[2];
	localObjs[0] = plainText;

	stmts(localObjs);
	
	return genString(localObjs);
    }    
}
