package Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String KEY_VALUE = "password_ultra_S$cret@";
    private static final byte[] KEY_BYTES = KEY_VALUE.getBytes();

    private static final long TOKEN_TIME_MINUTES = 5;
    private static final long EXPIRATION_TIME = TOKEN_TIME_MINUTES * 60 * 1000;

    public static final String SUB = "Authenticator";
    public static final String ISS = "System";

    public static String createJWT(String jti) {
        long current = System.currentTimeMillis();
        Date exp = new Date(current + EXPIRATION_TIME);
        Date iat = new Date(current);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Key signingKey = new SecretKeySpec(KEY_BYTES, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().
                setId(jti).
                setSubject(SUB).
                setIssuer(ISS).
                setExpiration(exp).
                setIssuedAt(iat).
                signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_BYTES)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
