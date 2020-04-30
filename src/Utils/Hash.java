package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {

    public final static String HASH_ALG = "SHA-512";

    public final static String SALT = "o8qw`^*ASDeygfSD`!#$Fpw21389qi*`*ªdfbhsFSDahd341233fbk";

    public static String get(String input, String salt) {
        try {
            MessageDigest hashF = MessageDigest.getInstance(HASH_ALG);
            String in = input + salt + SALT;
            byte[] bytes = in.getBytes();
            byte[] hash = hashF.digest(bytes);
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            Log.error("Hash error.");
            return null;
        }
    }

    public static int length() {
        try {
            MessageDigest hashF = MessageDigest.getInstance(HASH_ALG);
            return hashF.getDigestLength();
        } catch (NoSuchAlgorithmException e) {
            Log.error("Hash length error.");
            return 0;
        }
    }

}
