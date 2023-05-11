package server.managers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordManager {
    private static final String algorithmName = "SHA-384";
    public static final int saltLength = 2;

    public static final String pepper = "*63&^mVLC(#";

    public static String getSalt() {
        return "5555";
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[saltLength];
//        random.nextBytes(salt);
//        StringBuilder res = new StringBuilder();
//        for (Byte b : salt) {
//            res.append((char) (b + 128));
//        }
//        return res.toString();
//        return Arrays.toString(salt);
//        return new String(salt);
//        return Base64.getEncoder().encodeToString(salt);
//        return String.valueOf(salt);
    }

    public static String getHash(String password, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
//        String salt = getSalt();
        try {
//            byte[] hash = md.digest((pepper + password + salt).getBytes("UTF-8"));
            byte[] hash = md.digest((password).getBytes("UTF-8"));
            for (byte c : hash) {
                System.out.print((char)c);
            }
            System.out.println();
            System.out.println("----------------");
            return hash.toString();
        } catch (UnsupportedEncodingException e) {
            return ";";
        }

    }

    public static String getHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
//        String salt = getSalt();
        try {
//            byte[] hash = md.digest((pepper + password + salt).getBytes("UTF-8"));
            byte[] hash = md.digest((password).getBytes("UTF-8"));
            for (byte c : hash) {
                System.out.print((char)c);
            }
            System.out.println();
            System.out.println("----------------");
            return hash.toString();
        } catch (UnsupportedEncodingException e) {
            return  ";";
        }

    }
}
