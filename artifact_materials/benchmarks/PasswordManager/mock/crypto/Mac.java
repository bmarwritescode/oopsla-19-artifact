public class Mac {
    byte[] mac;

    public Mac() {
	mac = new byte[16];
    }

    public static Mac getInstance(String type) {
	return new Mac();
    }

    public void init(SecretKeySpec key) {
	// generate some random stuff to put in the MAC
	byte[] newMac = new byte[16];
	int sum = 0;
	for (int i=0; i<newMac.length; i++) {
	    newMac[i] = i+sum;
	    sum += i;
	}
	mac = newMac;
    }

    public byte[] doFinal(byte[] text) {
	return text;
    }
}
