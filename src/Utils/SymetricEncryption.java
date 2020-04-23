package Utils;

import Config.Configs;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SymetricEncryption {

    private static final String ALGO = "AES";
    private static final Key KEY = new SecretKeySpec(Configs.SYM_ENCRYPTION_KEY.getBytes(), ALGO);

    public static String encrypt(String Data) {
        try {
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, KEY);
            byte[] encVal = c.doFinal(Data.getBytes());
            return java.util.Base64.getEncoder().encodeToString(encVal);
        }catch (Exception e){
            Log.error("Encrypt error.");
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, KEY);
            byte[] decodedValue = java.util.Base64.getDecoder().decode(encrypted);
            byte[] decValue = c.doFinal(decodedValue);
            return new String(decValue);
        }catch (Exception e){
            Log.error("Decrypt error.");
        }
        return null;
    }

}
