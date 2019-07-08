package com.nucypher.kafka.cipher;

import com.nucypher.kafka.errors.CommonException;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.utils.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Properties;

/**
 * OpenSSL cipher
 */
public class OpenSSLCipher implements ICipher {

    private String transformation;
    private String algorithm;

    /**
     * @param algorithm      algorithm
     * @param transformation transformation string
     */
    public OpenSSLCipher(String algorithm, String transformation) {
        this.algorithm = algorithm;
        this.transformation = transformation;
    }

    private Cipher getCipher(boolean isEncryption, SecretKeySpec key, byte[] IV) {
        Properties properties = new Properties();
        properties.setProperty("CLASSES_KEY", CryptoCipherFactory.CipherProvider.getClassName());
        Cipher cipher;
	cipher = Utils.getCipherInstance(transformation, properties);	

        SecretKeySpec keyValue = new SecretKeySpec();
        AlgorithmParameterSpec IVspec = new IvParameterSpec(IV);

	if (isEncryption) {
	    cipher.init(??, key, IVspec);		
	} else {
	    cipher.init(??, key, IVspec);		
	}

        return cipher;
    }

    /**
     * Encrypt data using DEK and IV
     *
     * @param data data for encryption
     * @param key  Data Encryption Key
     * @param IV   initialization vector
     * @return encrypted data
     */
    public Object encrypt(byte[] data, SecretKeySpec key, byte[] IV) {
        return translate1(true, data, key, IV);
    }

    /**
     * Decrypt data using DEK and IV
     *
     * @param data data for decryption
     * @param key  Data Encryption Key
     * @param IV   initialization vector
     * @return decrypted data
     */
    @boxedArgs(1)
    public byte[] decrypt(byte[] data, SecretKeySpec key, byte[] IV) {
        return translate2(false, data, key, IV);
    }

    private Object translate1(boolean isEncryption, byte[] data, SecretKeySpec key, byte[] IV) {
	int[] localInts = new int[2];
	boolean[] localBools = new boolean[1];
	localBools[0] = isEncryption;
	Object[] localObjs = new Object[5];
	localObjs[0] = new Bytes(data);
	localObjs[1] = key;
	localObjs[2] = new Bytes(IV);
	localObjs[3] = new Bytes(new byte[1]);

	localObjs[4] = getCipher(isEncryption, key, IV);	
	Cipher cipher = (Cipher) localObjs[4];	
	Bytes blah = localObjs[3];
	byte[] output = blah.toArray();
	@box
	byte[] tmp = data;
	localInts[0] = cipher.update(tmp, 0, 0, output, 0);
	localInts[1] = cipher.doFinale(tmp, 0, 0, output, localInts[0]);
	Cipher c = (Cipher) localObjs[4];
	return c.doFinal(tmp);
    }

    @boxedArgs(2)
    private byte[] translate2(boolean isEncryption, byte[] data, SecretKeySpec key, byte[] IV) {
	int[] localInts = new int[2];
	boolean[] localBools = new boolean[1];
	localBools[0] = isEncryption;
	Object[] localObjs = new Object[5];
	localObjs[0] = data;
	localObjs[1] = key;
	localObjs[2] = new Bytes(IV);
	localObjs[3] = new Bytes(new byte[1]);	

	localObjs[4] = getCipher(isEncryption, key, IV);
	Cipher cipher = (Cipher) localObjs[4];
	Bytes blah = localObjs[3];
	byte[] output = blah.toArray();
	localInts[0] = cipher.update(data, 0, 1, output, 0);
	localInts[1] = cipher.doFinale(data, 0, 0, output, localInts[0]);
	Cipher c = (Cipher) localObjs[4];
	@unbox
	byte[] blah2 = c.doFinal(data);
	return Arrays.copyOf(blah2, localInts[0]+localInts[1]);
    }

}
