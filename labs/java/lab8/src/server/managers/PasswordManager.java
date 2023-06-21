package server.managers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Класс для генерации хэша для пароля.
 */
public class PasswordManager {
    private static final String algorithmName = "SHA-384";
    private static final int hashLength = 96;

    private static final String pepper = "*63&^mVLC(#";

    /**
     * Генерирует соль для пароля
     *
     * @return String - соль (случайное шестнадцатеричное число)
     */
    public static String getSalt() {
        return Integer.toHexString(new Random().nextInt());
    }

    /**
     * Генерирует хэш пароля
     *
     * @param password - пароль
     * @param salt - соль для каждого пользователя своя
     * @return String - хэш из соли, пароля, перца
     */
    public static String getHash(String password, String salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = md.digest((salt + password + pepper).getBytes(StandardCharsets.UTF_8));

        BigInteger bigInt = new BigInteger(1, hash);

        StringBuilder strHash = new StringBuilder(bigInt.toString(16));

        while (strHash.length() < hashLength) {
            strHash.insert(0, "0");
        }

        return strHash.toString();
    }
}
