public class Kafka_Tester {
    harness public void main() {
	testOpenSSL();
    }
    
    public void testOpenSSL() {
	OpenSSLCipher oc = new OpenSSLCipher("AES", "TRANSFORMATION");

	for (int x = 0; x < 5; x++) {		    	    
	    String p1 = Integer.toString(x);
	    String p2 = Integer.toString(x+1);
	    String p3 = Integer.toString(x+2);
	    
	    byte[] plaintext = p1.getBytes();
	    byte[] IV = p2.getBytes();
	    byte[] key = p3.getBytes();
	    
	    SecretKeySpec sk = new SecretKeySpec();
	    @isBoxed
	    byte[] cipherText = oc.encrypt(plaintext, sk, IV);

	    assert !(cipherText instanceof Bytes);	    
	    
	    byte[] plaintext2 = oc.decrypt(cipherText, sk, IV);	
	    assert Arrays.arraysEquals(plaintext, plaintext2);
	}
    }
}
