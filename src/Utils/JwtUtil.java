package Utils;

import Config.Configs;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final byte[] KEY_BYTES = Configs.JWT_KEY_VALUE.getBytes();

    private static final long JWT_EXPIRATION_TIME = Configs.JWT_TIME_MINUTES * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = Configs.REFRESH_TOKEN_TIME_HOURS * 60 * 60 * 1000;

    public static final String SUB = "Authentication";
    public static final String JWT_TYPE = "JWT";
    public static final String REFRESH_TOKEN_TYPE = "Refresh-token";

    public static String createJWT(String jti) {
        return createToken(jti, JWT_EXPIRATION_TIME, JWT_TYPE);
    }

    public static String createJWTRefreshToken(String jti) {
        return createToken(jti, REFRESH_TOKEN_EXPIRATION_TIME, REFRESH_TOKEN_TYPE);
    }

    public static String parseJWT(String encryptJWT) throws SignatureException, ExpiredJwtException{
        String jwt;
        if(Configs.ENCRYPT_TOKENS)
            jwt = SymetricEncryption.decrypt(encryptJWT);
        else
            jwt = encryptJWT;
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_BYTES)
                .parseClaimsJws(jwt).getBody();
        return claims.getId();
    }

    private static String createToken(String jti, long expirationTime, String tokenType) {
        long current = System.currentTimeMillis();
        Date exp = new Date(current + expirationTime);
        Date iat = new Date(current);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Key signingKey = new SecretKeySpec(KEY_BYTES, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().
                setId(jti).
                setSubject(SUB).
                setHeaderParam("type", tokenType).
                setExpiration(exp).
                setIssuedAt(iat).
                signWith(signatureAlgorithm, signingKey);

        String jwt = builder.compact();
        if(Configs.ENCRYPT_TOKENS)
            return SymetricEncryption.encrypt(jwt);
        return jwt;
    }
}
