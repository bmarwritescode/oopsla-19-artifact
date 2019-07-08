public class CipherFactoryTests {
    harness public void main() {
    	CryptoManager cm = new CryptoManager();


    	for (int x = 9; x < 5000000; x = x * 9) {	
    	    String m = Integer.toString(x);	
    	    String d = cm.encrypt(m);
    	    assert !m.equals(d);
    	    String p = cm.decrypt(d);
    	    assert p.equals(m);	
    	}
    }
}
