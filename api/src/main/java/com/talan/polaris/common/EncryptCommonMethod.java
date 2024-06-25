package com.talan.polaris.common;

import com.talan.polaris.utils.encryption.CryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.talan.polaris.constants.CommonConstants.PASS_PHRASE;

public class EncryptCommonMethod {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptCommonMethod.class);

    /**
     * @param encryptedValue
     * @return
     */
    public static Double decryptStringToDouble(String encryptedValue) {
        String decrypted = null;
        try {

            if (encryptedValue != null) {
                LOGGER.info("Decrypt encrypted string value to double");
                decrypted = CryptUtils.getInstance(PASS_PHRASE).decrypt(encryptedValue);
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return Double.parseDouble(decrypted);

    }

    /**
     * @param value
     * @return
     */
    public static String encryptDouble(Double value) {
        String convertedValueToString = null;
        try {
            LOGGER.info("Encrypt double value to string");
            convertedValueToString = Double.toString(value);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return CryptUtils.getInstance(PASS_PHRASE).encrypt(convertedValueToString);

    }
}
