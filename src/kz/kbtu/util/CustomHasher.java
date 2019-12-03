package kz.kbtu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomHasher {

    private static CustomHasher hasher = null;

    private CustomHasher() {

    }

    public static CustomHasher getInstance() {
        if (hasher == null) {
            hasher = new CustomHasher();
        }

        return hasher;
    }

    private MessageDigest digest;

    {
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String hash(String string) {
        digest.update(string.getBytes());

        byte[] bytes = digest.digest();

        StringBuilder builder = new StringBuilder();

        for (byte aByte : bytes) {
            builder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        return builder.toString();
    }

}
