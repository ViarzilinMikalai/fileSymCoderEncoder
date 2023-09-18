package org.verzilin.encrypterDecrypter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncrypterDecrypterImpl implements FileEncrypterDecrypter {
    private final SecretKey secret = KeyGenerator.getInstance("AES").generateKey();
    private final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    public FileEncrypterDecrypterImpl() throws NoSuchAlgorithmException, NoSuchPaddingException {
    }

    @Override
    public void encrypt(File file){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secret);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] iv = cipher.getIV();
        File newFile = new File(file.getAbsolutePath() + ".enc");


        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fileOut = new FileOutputStream(newFile);
             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {

            fileOut.write(iv);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = fis.read(bytes)) != -1) {
                cipherOut.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void decrypt(File fileName){
        File newFile = new File(fileName + ".dec");
        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            byte[] fileIv = new byte[16];
            fileIn.read(fileIv);
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(fileIv));

            try (
                    CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                    BufferedInputStream bis = new BufferedInputStream(cipherIn);
                    FileOutputStream fileOut = new FileOutputStream(newFile);
            ) {
                int read;
                byte[] bytes = new byte[1024];
                while ((read = bis.read(bytes)) != -1) {
                    fileOut.write(bytes, 0, read);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
