package com.project.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




@Component
public class JwtTokenHelper {


    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    private String secret = "jwtTokenKey";

    
    
    
    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setEmail(body.getSubject());//SetUsernameIs method here
            u.setId(Long.parseLong((String) body.get("userId")));
          

            return u;

        } catch (ClassCastException e) {
            return null;
        }
    }
    
    
    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
    	System.out.println("===============");
    	System.out.println(userDetails.getUsername());
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

    	
//      return Jwts.builder()
//    			  .setIssuer(subject)
//    			  .setSubject(subject)
//    			  .claim("name", subject)
////    			  .claim("scope", "admins")
//    			  // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
//    			  .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
//    			  // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
//    			  .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
//    			  .signWith(
//    			    SignatureAlgorithm.HS256,
//    			    TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
//    			  )
//    			  .compact();
    	
    	 return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                 .signWith(SignatureAlgorithm.HS512, secret).compact();
       
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
    	
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	
}
