package com.codejago.jasyptspringboot.encryptor;

/**
 * {@link org.jasypt.encryption.StringEncryptor} version of {@link com.codejago.jasyptspringboot.encryptor.SimpleAsymmetricByteEncryptor} that just relies on
 * delegation from {@link com.codejago.jasyptspringboot.encryptor.ByteEncryptorStringEncryptorDelegate} and provides a constructor for {@link com.codejago.jasyptspringboot.encryptor.SimpleAsymmetricConfig}
 *
 * @author Ulises Bocchio
 * @version $Id: $Id
 */
public class SimpleGCMStringEncryptor extends ByteEncryptorStringEncryptorDelegate {

    /**
     * <p>Constructor for SimpleGCMStringEncryptor.</p>
     *
     * @param delegate a {@link com.codejago.jasyptspringboot.encryptor.SimpleAsymmetricByteEncryptor} object
     */
    public SimpleGCMStringEncryptor(SimpleAsymmetricByteEncryptor delegate) {
        super(delegate);
    }

    /**
     * <p>Constructor for SimpleGCMStringEncryptor.</p>
     *
     * @param config a {@link com.codejago.jasyptspringboot.encryptor.SimpleGCMConfig} object
     */
    public SimpleGCMStringEncryptor(SimpleGCMConfig config) {
        super(new SimpleGCMByteEncryptor(config));
    }
}
