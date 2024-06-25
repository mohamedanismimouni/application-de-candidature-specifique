package com.talan.polaris.utils.encryption;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.talan.polaris.constants.IntNumberConstant;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CryptUtils{
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptUtils.class);


    private static final String INIT_VECTOR = "encryptionIntVec";

    private SecretKey key;

    private static final String KEY_ALGORITHM = "AES";

    private static final String ENCRYPT_ALGORITHM = "AES/CBC/PKCS5PADDING";

    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";

    private static CryptUtils encrypterInstance;

    private CryptUtils(String passPhrase) {
        /* génération du salt */
        byte[] salt = { IntNumberConstant.C_108, -IntNumberConstant.C_17, -IntNumberConstant.C_65,
                -IntNumberConstant.C_67, -IntNumberConstant.C_17, -IntNumberConstant.C_65, -IntNumberConstant.C_67,
                -IntNumberConstant.C_17, -IntNumberConstant.C_65, -IntNumberConstant.C_67, -IntNumberConstant.C_17,
                -IntNumberConstant.C_65, -IntNumberConstant.C_67, IntNumberConstant.C_9, IntNumberConstant.C_70,
                IntNumberConstant.C_75, IntNumberConstant.C_63, -IntNumberConstant.C_17, -IntNumberConstant.C_65,
                -IntNumberConstant.C_67, -IntNumberConstant.C_17, -IntNumberConstant.C_65, -IntNumberConstant.C_67,
                IntNumberConstant.C_107, -IntNumberConstant.C_17, -IntNumberConstant.C_65, -IntNumberConstant.C_67,
                IntNumberConstant.C_114, IntNumberConstant.C_94, IntNumberConstant.C_65, -IntNumberConstant.C_17,
                -IntNumberConstant.C_65, -IntNumberConstant.C_67, -IntNumberConstant.C_62, -IntNumberConstant.C_81,
                -IntNumberConstant.C_17, -IntNumberConstant.C_65, -IntNumberConstant.C_67 };
        try {
            /* Derive the key, given password and salt. */
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
            KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, 65536, 256);
            key = factory.generateSecret(spec);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("ErrorFilter: l'algorithme utilisé n'existe pas");
        } catch (InvalidKeySpecException e) {
            LOGGER.error("ErrorFilter: Clé invalide");
        }

    }

    public static CryptUtils getInstance(String passPhrase) {
        if (encrypterInstance == null) {
            encrypterInstance = new CryptUtils(passPhrase);
        }
        return encrypterInstance;
    }


    /**
     * Cryptage.
     *
     * @param str
     *            chaîne à crypter
     * @return chaîne cryptée
     */
    public String encrypt(String str) {
        try {
            IvParameterSpec iv = createIvParameterSpec();
            SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(str.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            LOGGER.error("ErrorFilter: Erreur de cryptage " + ex.getMessage());
        }
        return null;
    }

    /**
     * Décryptage.
     *
     * @param str
     *            chaîne à décrypter
     * @return chaîne décryptée
     */
    public String decrypt(String str) {
        if (str == null) {
            return null;
        }
        try {
            IvParameterSpec iv = createIvParameterSpec();
            SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(str));

            return new String(original);
        } catch (Exception ex) {
            LOGGER.error("ErrorFilter: Erreur de décryptage " + ex.getMessage());
        }

        return null;
    }

    public Map<String, String> decryptParams(String params) {
        params = params.replaceAll(" ", "+");
        String paramsDecrypted = createAndDecrypt(params);
        Map<String, String> finalParams = new HashMap<>();
        Arrays.asList(paramsDecrypted.split(";"))
                .forEach(item -> finalParams.put(item.split("=")[0], item.split("=")[1]));
        return finalParams;
    }

    public byte[] encryptDocument(byte[] imageInByte) {
        try {
            IvParameterSpec iv = createIvParameterSpec();
            SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(imageInByte);
        } catch (Exception ex) {
            LOGGER.error("ErrorFilter: Erreur de cryptage " + ex.getMessage());
        }
        return new byte[0];
    }

    public byte[] decryptDocument(byte[] encImageInByte) throws IllegalBlockSizeException, BadPaddingException {
        if (encImageInByte == null) {
            return new byte[0];
        }
        try {
            IvParameterSpec iv = createIvParameterSpec();
            SecretKeySpec skeySpec = new SecretKeySpec(key.getEncoded(), KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(encImageInByte);
        } catch (Exception ex) {
            LOGGER.error("ErrorFilter: Erreur de décryptage " + ex.getMessage());
        }

        return new byte[0];
    }


    private IvParameterSpec createIvParameterSpec()
            throws UnsupportedEncodingException {
        return new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8.name()));
    }

    public byte[] createAndEncryptDocument(byte[] document) {
        return encryptDocument(document);
    }

    public byte[] createAndDecryptDocument(byte[] document)
            throws IllegalBlockSizeException, BadPaddingException {
        return decryptDocument(document);
    }

    /**
     * Cryptage.
     *
     * @param str
     *            chaîne à crypter
     * @return chaîne cryptée
     */
    public String createAndEncrypt(String str) {
        return encrypt(str);
    }

    /**
     * Décryptage.
     *
     * @param str
     *            chaîne à décrypter
     * @return chaîne décryptée
     */
    public String createAndDecrypt(String str) {
        return decrypt(str);
    }

    /**
     * Cryptage.
     *
     * @param str
     *            chaîne à crypter
     * @return chaîne cryptée
     */
    public String createAndEncryptUrl(String str) {
        return encrypt(str);
    }

    /**
     * Décryptage.
     *
     * @param str
     *            chaîne à décrypter
     * @return chaîne décryptée
     */
    public String createAndDecryptUrl(String str) {
        return decrypt(str);
    }

    public static String hash256(String data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte[] digest = md.digest();
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("ErrorFilter: l'algorithme utilisé n'existe pas");
        }
        return null;

    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            byt = (byte) (byt < 127 ? byt + 1 : -128);
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}