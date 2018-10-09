package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;

import com.google.common.base.Charsets;
import org.junit.Test;
import seedu.address.commons.exceptions.CorruptedFileException;
import seedu.address.commons.exceptions.InvalidPasswordException;

public class DataSecurityUtilTest {

    private String PASSWORD = "p@ssW0Rd";
    private String WRONG_PASSWORD = "1";

    private byte[] TEST_DATA_ENCRYPTION = "TEST DATA".getBytes();
    private String EXPECTED_ENCRYPTION = "D91B9E39621EACE5877DCD6158393F0F";

    private byte[] TEST_DATA_DECRYPTION = convertStringToByteArray("D91B9E39621EACE5877DCD6158393F0F");
    private String EXPECTED_DECRYPTION = "TEST DATA";

    // Encryption test case
    @Test
    public void testEncryptingBytes(){
        byte[] encryptedData = DataSecurityUtil.encrypt(TEST_DATA_ENCRYPTION, PASSWORD);
        String encryptedDataString = convertByteArrayToHexString(encryptedData);

        assertEquals(EXPECTED_ENCRYPTION, encryptedDataString);
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptingNullBytesValidPassword(){
        DataSecurityUtil.encrypt(null, PASSWORD);
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptingValidBytesAndNullPassword(){
        DataSecurityUtil.encrypt(TEST_DATA_ENCRYPTION, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptingNullBytesAndNullPassword(){
        DataSecurityUtil.encrypt(null, null);
    }

    // Decryption test case
    @Test
    public void testDecryptingBytes() throws CorruptedFileException, InvalidPasswordException {
        byte[] decryptedData = DataSecurityUtil.decrypt(TEST_DATA_DECRYPTION, PASSWORD);
        String decryptedDataString = new String(decryptedData, Charsets.UTF_8);

        assertEquals(EXPECTED_DECRYPTION, decryptedDataString);
    }

    @Test(expected = NullPointerException.class)
    public void testDecryptingNullBytesValidPassword() throws CorruptedFileException, InvalidPasswordException {
        DataSecurityUtil.decrypt(null, PASSWORD);
    }

    @Test(expected = NullPointerException.class)
    public void testDecryptingValidBytesAndNullPassword(){
        DataSecurityUtil.encrypt(TEST_DATA_DECRYPTION, null);
    }

    @Test(expected = NullPointerException.class)
    public void testDecryptingNullBytesAndNullPassword(){
        DataSecurityUtil.encrypt(null, null);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testDecryptingBytesWrongPassword() throws CorruptedFileException, InvalidPasswordException {
        DataSecurityUtil.decrypt(TEST_DATA_DECRYPTION, WRONG_PASSWORD);
    }
    
    public static String convertByteArrayToHexString(byte[] data) {
        return DatatypeConverter.printHexBinary(data);
    }

    public static byte[] convertStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
