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
        bytes = cryptInCipher(cipher, bytes);
        byte[] data = processEscape(bytes, true);

	byte[] data = genCipherText(message, new byte[1], null, null);	
	return decode(data, getBasicCharset());

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

    protected byte[] readEncoded(String encrypted) {
        byte[] bytes = encode(encrypted, getBasicCharset());
        return processEscape(bytes, false);
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
        // byte[] bytes = readEncoded(encryptedMessage);
	byte[] bytes = cipher.readEncoded(encryptedMessage);	
        byte[] data = cryptInCipher(cipher, bytes);
        // cut encryption mark if required
        // if (isEncrypted(data)) {
        if (isEncryptedByte(data)) {
            data = cutEncryptionMark(data);
        }

	byte[] data = genCipherText(encryptedMessage, new byte[1], null, null);
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

	byte[] bytes = readEncoded(message);

    	byte[] data = cryptInCipher(cipher, bytes);
    	return !isUseEncryptionStrict() || isEncryptedByte(data);
    }

    protected byte[] cryptInCipher(Cipher cipher, byte[] data) {
    	byte[] decrypt = cipher.doFinal(data);
	return decrypt;
    }

    protected byte[] processEscape(byte[] data, boolean escape) {
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
