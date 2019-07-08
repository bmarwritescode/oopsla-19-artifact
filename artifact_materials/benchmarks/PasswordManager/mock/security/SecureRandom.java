public class SecureRandom {
    byte[] key;
    
    public SecureRandom() {
	key = new byte[16];
    }

    public void nextBytes(byte[] buf) {
	// try to approximate some randomness with some weird operations
	int sum = 0;
	for (int i = 0; i < buf.length; i++) {
	    int j = i + sum;
	    if (i % 2 == 0) {
		j = i * i;
	    }
	    if (i % 3 == 0) {
		j = i - sum;
	    }
	    if (i % 5 == 0) {
		j = 42;
	    }
	    byte nextByte = (byte) j;
	    buf[i] = nextByte;
	    if (i % 7 == 0) {
		sum += j;
	    }
	}
    }
}
