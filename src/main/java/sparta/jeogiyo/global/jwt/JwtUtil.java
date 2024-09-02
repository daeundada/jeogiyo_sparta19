package sparta.jeogiyo.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sparta.jeogiyo.domain.user.entity.UserRoleEnum;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public final String AUTHORIZATION_HEADER = "Authorization";

    public final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.expiration-time}")
    private long EXPIRATION_TIME;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username, List<UserRoleEnum> roles) {
        Date date = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return BEARER_PREFIX +
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + EXPIRATION_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public void addJwtToCookie(String token, HttpServletResponse res) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8);

        Cookie jwtCookie = new Cookie(AUTHORIZATION_HEADER, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) EXPIRATION_TIME / 1000);

        res.addCookie(jwtCookie);
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken)) {
            String decodedToken = URLDecoder.decode(bearerToken, StandardCharsets.UTF_8);
            if (decodedToken.startsWith(BEARER_PREFIX)) {
                return decodedToken.substring(7);
            }
        }
        return "";
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (AUTHORIZATION_HEADER.equals(cookie.getName())) {
                String decodedToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                if (decodedToken.startsWith(BEARER_PREFIX)) {
                    return decodedToken.substring(7);
                }
            }
        }
        return "";
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}