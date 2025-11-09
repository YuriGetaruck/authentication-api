package yurigetaruck.authentication.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import yurigetaruck.authentication.modules.auth.role.model.Role;
import yurigetaruck.authentication.modules.auth.user.model.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class TokenConfig {

    //TODO - TORNAR VARIAVEL DE AMBIENTE
    private final String secret = "secret";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(User user) {
        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("roles", user.getRoles().stream().map(Role::getCode).toList())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try {
            var decode = JWT.require(algorithm).build().verify(token);
            return Optional.of(new JWTUserData(decode.getClaim("userId").asInt(),
                    decode.getSubject(),
                    decode.getClaim("roles").asList(String.class)));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
