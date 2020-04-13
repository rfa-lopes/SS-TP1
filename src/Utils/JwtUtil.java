package Utils;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String KEY_VALUE = "password_ultra_S$cret@--123456";
    private static final byte[] KEY_BYTES = KEY_VALUE.getBytes();

    //TODO: change to 5 or 15
    private static final long JWT_TIME_MINUTES = 1;
    private static final long REFRESH_TOKEN_TIME_MINUTES = 60 * 5; //5 horas

    private static final long JWT_EXPIRATION_TIME = JWT_TIME_MINUTES * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = REFRESH_TOKEN_TIME_MINUTES * 60 * 1000;

    public static final String SUB = "Authentication";
    public static final String JWT_TYPE = "JWT";
    public static final String REFRESH_TOKEN_TYPE = "Refresh-token";

    public static String createJWT(String jti) {
        return createToken(jti, JWT_EXPIRATION_TIME, JWT_TYPE);
        //return SymetricEncryption.encrypt(createToken(jti, JWT_EXPIRATION_TIME, JWT_TYPE));
    }

    public static String createJWTRefreshToken(String jti) {
        return createToken(jti, REFRESH_TOKEN_EXPIRATION_TIME, REFRESH_TOKEN_TYPE);
        //return SymetricEncryption.encrypt(createToken(jti, REFRESH_TOKEN_EXPIRATION_TIME, REFRESH_TOKEN_TYPE));
    }

    public static String parseJWT(String encryptJWT) throws SignatureException, ExpiredJwtException{
        //String jwt = SymetricEncryption.decrypt(encryptJWT);
        String jwt = encryptJWT;
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_BYTES)
                .parseClaimsJws(jwt).getBody();
        return claims.getId();
    }

    private static String createToken(String jti, long refreshTokenExpirationTime, String refreshTokenType) {
        long current = System.currentTimeMillis();
        Date exp = new Date(current + refreshTokenExpirationTime);
        Date iat = new Date(current);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Key signingKey = new SecretKeySpec(KEY_BYTES, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().
                setId(jti).
                setSubject(SUB).
                setHeaderParam("type", refreshTokenType).
                setExpiration(exp).
                setIssuedAt(iat).
                signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }
}
