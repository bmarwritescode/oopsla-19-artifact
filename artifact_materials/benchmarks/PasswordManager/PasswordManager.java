import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.sql.Savepoint;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.sasl.AuthenticationException;

public class PasswordManager {

	/**
	 * @param args
	 * @throws AuthenticationException
	 */

	private static PasswordManager passManager;
	private Cryptographer cryptographer;
	private PasswordMap passMap;

        public static PasswordManager getPassManager(String masterPassword) {
		if (passManager != null)
			return passManager;
		passManager = new PasswordManager(masterPassword);
		return passManager;
	}

    
	// authentication is required only on creation
	public PasswordManager(String masterPassword) {
		cryptographer = new Cryptographer();
		passMap = new PasswordMap();
	}

	private boolean checkMasterPassword(String masterPassword) {
		// TODO Auto-generated method stub
		String passHash = cryptographer.hash(masterPassword);
		if (passHash.equals(passMap.masterHash))
			return true;
		return false;
	}
    
	public void addPassword(String domain, String passWord){
		String PassEncryption = cryptographer.encrypt(passWord);
		String domainHash = cryptographer.hash(domain);
		passMap.add(domainHash, PassEncryption);
	}

    
	public void modifyPassword(String domain, String passWord) {
		String PassEncryption = cryptographer.encrypt(passWord);
		String domainHash = cryptographer.hash(domain);
		passMap.add(domainHash, PassEncryption);
	}

	public void deletePassword(String domain) {
		String domainHash = cryptographer.hash(domain);
		passMap.remove(domainHash);
	}

	public String getPass(String domain) {
		String domainHash = cryptographer.hash(domain);
		String PassEncryption = passMap.get(domainHash);
		return cryptographer.decrypt(PassEncryption);
	}

        public void close() {

	}

        public void changeMaster(String newMaster) throws FileNotFoundException{
		String passHash = cryptographer.hash(newMaster);
		passMap.masterHash = passHash;
	}
}
