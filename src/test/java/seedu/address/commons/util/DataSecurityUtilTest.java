package seedu.address.commons.util;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.commons.util.DataSecurityUtil.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Formatter;

import org.junit.Test;
import seedu.address.testutil.Assert;

public class DataSecurityUtilTest {

    private String PASSWORD = "p@ssW0Rd";

    private byte[] TEST_DATA = "1qazXSW@1qazXSW@".getBytes();
    private String EXPECTED_ENCRYPTION = "0d2989d11f8a7e8acf7c518335651dc0";
    private String EXPECTED_DECRYPTION = "1qazXSW@1qazXSW@";

    // Encryption test case
    @Test
    public void testEncryptingBytes(){
        byte[] encryptedData = DataSecurityUtil.encrypt(TEST_DATA, PASSWORD);
        String encryptedDataString = byteArrayToHex(encryptedData);

        assertEquals(EXPECTED_ENCRYPTION, encryptedDataString);
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptingNullBytesValidPassword(){
        DataSecurityUtil.encrypt(null, PASSWORD);
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptingNullBytesAndNullPassword(){
        DataSecurityUtil.encrypt(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptingValidBytesAndNullPassword(){
        DataSecurityUtil.encrypt(TEST_DATA, null);
    }



    private String byteArrayToHex(byte[] data) {
        Formatter formatter = new Formatter();
        for (byte eachByte : data) {
            formatter.format("%02x", eachByte);
        }
        return formatter.toString();
    }
}
