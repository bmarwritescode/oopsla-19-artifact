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

    /**
     * Performs message encryption.
     * @param message message
     * @return encrypted message
     */
    @Override
    public String encrypt(String message) {
	ICipherFactory cf = getCipherFactory();
        Cipher cipher = cf.encryptionCipher();
        byte[] bytes = encode(message, getCharset());
        bytes = appendEncryptionMark(bytes);

	@isBoxed
	byte[] blah = cryptInCipher1(cipher, bytes);

	@isBoxed
        byte[] data = processEscape(blah, true);

	return cipher.toString(data);
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
	ICipherFactory cf = getCipherFactory();
        Cipher cipher = cf.decryptionCipher();

	@isBoxed
	byte[] bytes = cipher.readEncoded(encryptedMessage);	

        byte[] data = cryptInCipher2(cipher, bytes);
        if (isEncryptedByte(data)) {
            data = cutEncryptionMark(data);
        }

        return decode(data, getCharset());
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
