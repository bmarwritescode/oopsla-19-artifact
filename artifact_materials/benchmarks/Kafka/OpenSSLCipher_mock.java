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

    private Cipher getCipher(boolean isEncryption, Key key, byte[] IV) {
        Properties properties = new Properties();
        properties.setProperty("CLASSES_KEY", CryptoCipherFactory.CipherProvider.getClassName());
        Cipher cipher;
	cipher = Utils.getCipherInstance(transformation, properties);	

        SecretKey keyValue = new SecretKeySpec(key.getEncoded(), algorithm);
        AlgorithmParameterSpec IVspec = new IvParameterSpec(IV);

	if (isEncryption) {
	    cipher.init(0, keyValue, IVspec);		
	} else {
	    cipher.init(1, keyValue, IVspec);		
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
    public byte[] encrypt(byte[] data, Key key, byte[] IV) {
        byte[] cipherText =  translate(true, data, key, IV);
	return cipherText;
    }

    /**
     * Decrypt data using DEK and IV
     *
     * @param data data for decryption
     * @param key  Data Encryption Key
     * @param IV   initialization vector
     * @return decrypted data
     */
    public byte[] decrypt(byte[] data, Key key, byte[] IV) {
        return translate(false, data, key, IV);
    }
    
    private byte[] translate(boolean isEncryption, byte[] data, Key key, byte[] IV) {
        // byte[] output = new byte[2 * data.length];
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
	localInts[0] = cipher.update(data, 0, 1, output, 0);
	localInts[1] = cipher.doFinal(data, 0, 0, output, localInts[0]);
	Cipher c = (Cipher) localObjs[4];
	byte[] blah2 = c.doFinal(data);
	return Arrays.copyOf(blah2, localInts[0]+localInts[1]);
    }

}
