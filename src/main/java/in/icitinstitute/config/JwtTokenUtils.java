package in.icitinstitute.config;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Configuration
public class JwtTokenUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	// Retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	//
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claim = getAllClaimsFromToken(token);
		return claimResolver.apply(claim);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// Generate token for user
	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("name", "gyan");
//		claims.put("role", "ADMIN");
		//return doGenerateToken(claims, userDetails.getUsername());
		return doGenerateToken(userDetails);
		
	}

	// While creating the token-
	// 1. Defines claims of the token, like issuer, Expiration, subject and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS compact
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private String doGenerateToken(UserDetails userDetails) {

		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		claims.put("scopes", userDetails.getAuthorities());

		return Jwts.builder().setClaims(claims).setIssuer("http://icitinstitute.in")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	// Validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Retrieve username form jwt token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// Check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expriration = getExpirationDateFromToken(token);
		return expriration.before(new Date());
	}

}
