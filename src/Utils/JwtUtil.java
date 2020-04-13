package Utils;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String KEY_VALUE = "password_ultra_S$cret@--123456";
    private static final byte[] KEY_BYTES = KEY_VALUE.getBytes();

    //TODO: change to 5 ou 15
    private static final long TOKEN_TIME_MINUTES = 1;
    private static final long EXPIRATION_TIME = TOKEN_TIME_MINUTES * 60 * 1000;

    public static final String SUB = "Authenticator";
    public static final String ISS = "System";

    public static String createJWT(String jti) {

        long current = System.currentTimeMillis();
        Date exp = new Date(current + EXPIRATION_TIME);
        Date iat = new Date(current);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

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

    public static String parseJWT(String jwt) throws SignatureException, ExpiredJwtException{
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_BYTES)
                .parseClaimsJws(jwt).getBody();
        return claims.getId();
    }
}
