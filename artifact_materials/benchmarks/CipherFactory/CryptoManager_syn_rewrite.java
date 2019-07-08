package com.cl.xlp.core.impl.common.crypt;


import com.cl.xlp.model.data.common.I18nMessage;
import com.cl.xlp.model.exceptions.XlpRuntimeException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class CryptoManager implements ICryptoManager {

    private String basicCharset = "US-ASCII";

    private String charset = "UTF-8";

    private byte encryptedMark = (byte) 129;

    private boolean useEncryptionStrict = true;

    private ICipherFactory cipherFactory;

    public CryptoManager() {
	basicCharset = "US-ASCII";	
	charset = "UTF-8";
	byte b = 'a';
	encryptedMark = b;
	useEncryptionStrict = true;
	cipherFactory = new DefaultCipherFactory();
    }

    generator public String genRetVal(String plainText, byte[] bytes, ICipherFactory cf, Cipher cipher, Object blah) {
    	if (??) { return plainText; }
    	if (??) { return cipher.toString(blah); }
    	if (??) { return decode(bytes, getCharset()); }
    	return null;
    }
    
    generator public Object setBlah(String plainText, byte[] bytes, ICipherFactory cf, Cipher cipher, Object blah) {
    	if (??) { return cipher.readEncoded(plainText); }
    	if (??) { return cryptInCipher1(cipher, bytes); }
    	if (??) { return processEscape(blah, true); }
    	return null;
    }
    
    generator public Cipher setCipher(String plainText, byte[] bytes, ICipherFactory cf, Cipher cipher, Object blah) {
    	if (??) { return cf.decryptionCipher(); }
    	if (??) { return cf.encryptionCipher(); }
    	if (??) { return new Cipher(""); }
    	return null;
    }
    
    generator public ICipherFactory setICipherFactory(String plainText, byte[] bytes, ICipherFactory cf, Cipher cipher, Object blah) {
    	if (??) { return getCipherFactory(); }
    	return null;
    }
    
    generator public byte[] setBytes(String plainText, byte[] bytes, ICipherFactory cf, Cipher cipher, Object blah) {
    	if (??) { return encode(plainText, getCharset()); }
    	if (??) { return appendEncryptionMark(bytes); }
    	if (??) { return cryptInCipher2(cipher, blah); }
    	if (??) {
    	    boolean guard = isEncryptedByte(bytes);
    	    if (guard) {
    		return cutEncryptionMark(bytes);
    	    }
    	}
    	return new byte[1];
    }
    
    generator public String genStmtsRet(String plainText, byte[] bytes, ICipherFactory cf, Cipher cipher, Object blah) {
	if (??) {
	    bytes = setBytes(plainText, bytes, cf, cipher, blah);
	}
	if (??) {
	    cf = setICipherFactory(plainText, bytes, cf, cipher, blah);
	}
	if (??) {
	    cipher = setCipher(plainText, bytes, cf, cipher, blah);
	}
	if (??) {
	    blah = setBlah(plainText, bytes, cf, cipher, blah);
	}
	if (??) {
	    return genRetVal(plainText, bytes, cf, cipher, blah);
	}
	if (??) {
	    return genStmtsRet(plainText, bytes, cf, cipher, blah);
	}
	return null;
    }

    /**
     * Performs message encryption.
     * @param message message
     * @return encrypted message
     */
    @Override
    public String encrypt(String message) {
	return genStmtsRet(message, new byte[1], null, null, null);
    }

    /**
     * Performs encryption if message is not already encrypted.
     * @param message  message to encrypt
     * @return  Encrypted message
     */
    @Override
    public String encryptIfNotEncrypted(String message) {
        if(!isEncrypted(message)){
            return encrypt(message);
        }
        return message;
    }

    //**
    //  * Appends encryption mark at first position of byte array.
    //  * @param bytesArray initial byte array
    //  * @return new extended byte array with appended encryption mark
    //  */
    protected byte[] appendEncryptionMark(byte[] bytesArray) {
        byte[] extendedBytes = new byte[bytesArray.length + 1];
        extendedBytes[0] = getEncryptedMark();
        // copy bytesArray into position 1 of extendedBytes (from pos 0 of bytesArray to bytesArray.length )
        System.arraycopy(bytesArray, 0, extendedBytes, 1, bytesArray.length);
        return extendedBytes;
    }

    protected byte[] cutEncryptionMark(byte[] bytesArray) {
        byte[] trimmedBytes = new byte[bytesArray.length - 1];
	for (int i = 1; i < bytesArray.length; i++) {
	    trimmedBytes[i-1] = bytesArray[i];
	}
        return trimmedBytes;
    }

    protected boolean isEncryptedByte(byte[] data) {	
        return data[0] == getEncryptedMark();
    }

    /**
     * Performs message decryption.
     * <br />
     * Works both for {@link #encryptedMark} present and not.
     * @param encryptedMessage encrypted message
     * @return decrypted message
     */
    @Override
    public String decrypt(String encryptedMessage) {
        if(!isEncrypted(encryptedMessage)){
            return encryptedMessage;
        }

	return genStmtsRet(encryptedMessage, new byte[1], null, null, null);
    }

    /**
     * Performs encryption check.
     * @param message data to encrypt
     * @return true if message contains encryption mark
     */
    @Override
    public boolean isEncrypted(String message) {
	ICipherFactory cf = getCipherFactory();
        Cipher cipher = cf.decryptionCipher();

	@isBoxed
	byte[] bytes = cipher.readEncoded(message);

    	byte[] data = cryptInCipher2(cipher, bytes);
    	return !isUseEncryptionStrict() || isEncryptedByte(data);
    }

    protected Object cryptInCipher1(Cipher cipher, byte[] data) {
	@box
	byte[] blah = data;

	@isBoxed
    	byte[] decrypt = cipher.doFinal(blah);
	return decrypt;
    }

    protected byte[] cryptInCipher2(Cipher cipher, Object data) {
	@unbox
    	byte[] decrypt = cipher.doFinal(data);
	return decrypt;
    }


    protected Object processEscape(Object data, boolean escape) {    
    	return data;
    }

    protected byte[] encode(String string, String charset) {
	return string.getBytes();
    }

    protected String decode(byte[] string, String charset) {
	return new String(string);
    }

    public String getBasicCharset() {
        return basicCharset;
    }

    public void setBasicCharset(String basicCharset) {
        this.basicCharset = basicCharset;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public byte getEncryptedMark() {
        return encryptedMark;
    }

    public ICipherFactory getCipherFactory() {
        return cipherFactory;
    }

    public void setCipherFactory(ICipherFactory cipherFactory) {
        this.cipherFactory = cipherFactory;
    }

    public boolean isUseEncryptionStrict() {
        return useEncryptionStrict;
    }

    public void setUseEncryptionStrict(boolean useEncryptionStrict) {
        this.useEncryptionStrict = useEncryptionStrict;
    }
}
