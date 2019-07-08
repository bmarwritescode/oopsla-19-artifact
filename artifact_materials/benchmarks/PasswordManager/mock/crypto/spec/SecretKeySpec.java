public class SecretKeySpec implements Key{

    private byte[] key;
    private string type;
    
    public SecretKeySpec(byte[] key, String type) {
	this.key = key;
	this.type = type;
    }

    public byte[] getEncoded() {
	return this.key;
    }
    
    
}
