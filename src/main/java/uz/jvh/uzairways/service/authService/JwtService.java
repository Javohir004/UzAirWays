package uz.jvh.uzairways.service.authService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.entity.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class JwtService {

    @Value("${jwt.key}")
    private String key;

    public String generateToken(User user) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .subject(user.getUsername())
                .claims(Map.of("authorities", user.getRole()))
                .compact();
    }

    /*public List<String> getRoles(User user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }*/

    public Map<String, Object> validateToken(String token){
        Claims payload = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(key.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        List<SimpleGrantedAuthority> roles = extractRoles(payload);
        return Map.of(
                "principal", payload.getSubject(),
                "roles", roles
        );
    }


    private List<SimpleGrantedAuthority> extractRoles(Claims payload) {
        ArrayList<String> authorities = (ArrayList<String>) payload.get("authorities");


        return authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
