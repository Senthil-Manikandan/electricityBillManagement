package com.project.electricityBillManagement.jwt;

import com.project.electricityBillManagement.enumeration.Role;
import com.project.electricityBillManagement.model.Register;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private static final String SECRET_KEY ="OFPg2CZEuh8wak4UyoKnwduwtLfhBFa3JNWQ2bR08lE=";
    public String extractUserName(String token) {
        return extractClaims(token,Claims::getSubject);
    }

//    public String generateToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(),userDetails);
//    }
    public String generateToken(UserDetails userDetails) {
        Role role = getRoleFromUserDetails(userDetails);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return generateToken(claims, userDetails);
    }

    private Role getRoleFromUserDetails(UserDetails userDetails) {
        // Assuming UserDetails has a method to retrieve the role
        // Modify this code based on your UserDetails implementation
        if (userDetails instanceof Register) {
            log.info("Inside get rolefrom register : {}",((Register) userDetails).getFirstname());
            Register customUserDetails = (Register) userDetails;
            return customUserDetails.getRole();
        }
        return null;
    }

    public Role getRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return Role.valueOf(claims.get("role", String.class));
    }



    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){

        return  Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSignInInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String userName = extractUserName(token);
        return(userName.equals(userDetails.getUsername()) && ! isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
