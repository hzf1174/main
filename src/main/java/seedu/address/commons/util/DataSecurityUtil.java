package seedu.address.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.hash.Hashing;

import seedu.address.commons.exceptions.InvalidPasswordException;

/**
 * Encrypts and decrypts data
 */
public class DataSecurityUtil {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_INSTANCE = "AES/ECB/PKCS5Padding";
    private static final String INVALID_PASSWORD_MESSAGE = "Invalid Password";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void encryptFile(File file, String password) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        byte[] encryptedFileContent = encrypt(fileContent, password);

        Files.write(file.toPath(), encryptedFileContent);

    }

    public static void decryptFile(File file, String password) throws IOException, InvalidPasswordException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        byte[] decryptedFileContent = decrypt(fileContent, password);

        Files.write(file.toPath(), decryptedFileContent);

    }


    /**
     * Encrypts the given data using a password
     *
     * @param data The data to be encrypted
     * @param password Used to encrypt data
     * @return byte[] of the encrypted data
     */
    public static byte[] encrypt(byte[] data, String password) {
        try {
            Key secretKey = generateSecretKey(password);

            Cipher aesCipher = Cipher.getInstance(CIPHER_INSTANCE);
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return aesCipher.doFinal(data);

        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Decrypts the data using a given password
     *
     * @param data The data to be decrypted
     * @param password Used to decrypt data
     * @return byte[] of the decrypted data
     * @throws InvalidPasswordException if an invalid password is supplied
     */
    public static byte[] decrypt(byte[] data, String password) throws InvalidPasswordException {
        try {
            Key secretKey = generateSecretKey(password);
            Cipher aesCipher = Cipher.getInstance(CIPHER_INSTANCE);
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedText = aesCipher.doFinal(data);

            return decryptedText;


        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e){
            handleBadPaddingException();
        }

        return null;

    }

    private static Key generateSecretKey(String password){
        return new SecretKeySpec(getFirst16Bytes(hash(password).getBytes()), ALGORITHM);
    }

    private static void handleBadPaddingException() throws InvalidPasswordException {
            throw new InvalidPasswordException(INVALID_PASSWORD_MESSAGE);
    }

    private static String hash(String password) {
        return Hashing.sha1().hashString(password, CHARSET).toString();
    }

    private static byte[] getFirst16Bytes(byte[] password){
        return Arrays.copyOf(password, 16);
    }

    public static void main(String[] args) {
        byte[] data = "hello".getBytes();
        try {
            File file = new File("hello.xml");
//            encryptFile(file, "peter");
            decryptFile(file, "peter1");

//            byte[] encrypted = DataSecurityUtil.encrypt(data, "910");
//            System.out.println(new String(encrypted, CHARSET));
//
//
//            byte[] decrypted = DataSecurityUtil.decrypt(encrypted, "910");
//            System.out.println(new String(decrypted, CHARSET));


            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}

