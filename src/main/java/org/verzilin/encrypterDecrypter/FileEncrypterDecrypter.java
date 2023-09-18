package org.verzilin.encrypterDecrypter;

import java.io.File;

public interface FileEncrypterDecrypter {
    void encrypt(File file);
    void decrypt(File file);

}
