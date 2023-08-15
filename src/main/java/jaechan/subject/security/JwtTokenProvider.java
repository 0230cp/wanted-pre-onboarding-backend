package jaechan.subject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jaechan.subject.user.User.CustomUserDetails;
import jaechan.subject.user.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserService userService;

    private static final String jwtSecret = "adsajgihoehjnoianfglkvansgdpnbjasnfgsopgjasnjnga";
    private long expTime = 30 * 60 * 1000L; //30분

    //JWT 토큰 생성
    public String createToken(String subject, long expTime){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretkeyBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        Key signingKey = new SecretKeySpec(secretkeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    //토큰 검증
    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
                    return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    //토큰에서 회원정보 추출
    public String getUser(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    //Request Header에서 token값 가져오기
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public Authentication getAuthentication(String token){
        CustomUserDetails customUserDetails = userService.loadUserByUsername(this.getUser(token));
        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }
}
