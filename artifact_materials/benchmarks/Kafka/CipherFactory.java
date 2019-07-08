package com.nucypher.kafka.cipher;

import com.nucypher.kafka.Constants;
import com.nucypher.kafka.errors.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for {@link ICipher}
 */
public class CipherFactory {

    public static ICipher getCipher(String provider,
                                    String transformation) {
        String algorithm = transformation.split("/")[0];
	return new OpenSSLCipher(algorithm, transformation);	
    }
    
}
