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
    
        public Cryptographer()
        {
		SecureRandom random = new SecureRandom();

		byte[] randomKey = new byte[16];
		random.nextBytes(randomKey);
		
		GCMSecretKey = new SecretKeySpec();
		
		randomIv = new byte[16];
		random.nextBytes(randomIv);
		ivParameterSpec = new IvParameterSpec(randomIv);
				
		myCypherOut = new Cipher();
		myCypherOut.init(2, GCMSecretKey,ivParameterSpec);
		
		// init HMAC
		String secret = "secret";
		sha256_HMAC = new Mac();
		Hash_secret_key = new SecretKeySpec();
		sha256_HMAC.init(Hash_secret_key);

		MAX_LENGTH_PASSWORD = 16;
	}

    	public String hash(String plainTxt) {
    	    return sha256_HMAC.doFinal(plainTxt);
    	}

    	public String encrypt(String plainText)         {
    		myCypherOut.init(1, GCMSecretKey,ivParameterSpec);

		@box
		byte[] plainText_bytes = plainText.getBytes();

		@isBoxed
    		byte[] cipherText = myCypherOut.doFinal(plainText_bytes);

		return cipherText;
    	}

    	public String decrypt(String cipherText)    {

    		myCypherOut.init(2, GCMSecretKey,ivParameterSpec);

		@unbox
    		byte[] plainText = myCypherOut.doFinal(cipherText);
    		String decryptText = new String(plainText);  			

    		return decryptText;
    	}
}
