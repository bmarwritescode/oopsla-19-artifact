package com.cl.xlp.core.impl.common.crypt;

import com.cl.xlp.model.data.common.I18nMessage;
import com.cl.xlp.model.exceptions.XlpRuntimeException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * Default cipher factory using AES.
 */
public class DefaultCipherFactory implements ICipherFactory {

    protected String ALGORITHM;
    protected String PADDING;
    
    protected String algorithm;
    protected String padding;
    protected String key;
    protected boolean keyBase64;

    public DefaultCipherFactory() {
	ALGORITHM = "AES";
	PADDING = "AES/ECB/PKCS5Padding";
        algorithm = ALGORITHM;
        padding = PADDING;
	key = "KEY";
    }

    @Override
    public Cipher encryptionCipher() {
        return initCipher(1);
    }

    @Override
    public Cipher decryptionCipher() {
        return initCipher(2);
    }

    protected Cipher initCipher(int mode) {
        Cipher cipher;
    	cipher = obtainCipher(mode);	    
        return cipher;
    }

    protected Cipher obtainCipher(int mode) throws GeneralSecurityException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key(), getAlgorithm());
        Cipher cipher = Cipher.getInstance(getPadding());
        cipher.init(mode, secretKeySpec);
        return cipher;
    }

    protected byte[] key() {
	String k = getKey();
    	return k.getBytes();
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPadding() {
        return padding;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isKeyBase64() {
        return keyBase64;
    }

    public void setKeyBase64(boolean keyBase64) {
        this.keyBase64 = keyBase64;
    }
}
