package org.verzilin.encrypterDecrypter;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args){
        FileEncrypterDecrypter fed = null;
        try {
            fed = new FileEncrypterDecrypterImpl();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        fed.encrypt(new File("screen.jpg"));
        fed.decrypt(new File("screen.jpg.enc"));
    }
}
