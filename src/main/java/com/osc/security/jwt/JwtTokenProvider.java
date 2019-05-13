package com.osc.security.jwt;

/**
 * Created by Syarif Hidayat on 22/04/2019.
 */

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.osc.security.AppUserDetails;
import com.osc.server.model.Activity;
import com.osc.server.model.Token;
import com.osc.server.repository.IActivityRepository;
import com.osc.server.repository.ITokenRepository;

//import ch.qos.logback.classic.Logger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds; 

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
	ITokenRepository tokenRepository;
    
    @Autowired
   	IActivityRepository activityRepository;
    
    private String url;
    
    private Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String string) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", string);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, secretKey)//
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
    
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        	
        	/*
        	 * Set URL Activity
        	 * **/
        	url = req.getRequestURI();
        	
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            /*
             * get Current login data
             * **/
            Token currentUser = tokenRepository.findByUsername(this.getUsername(token));
            logger.info("Current login username:"+this.getUsername(token));
            
            if(!url.equals("") || !url.isEmpty()) {
            	Activity activity = new Activity(url, Instant.now(), currentUser);
                activityRepository.save(activity);
            } 
            
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
    
    
    /*
     * Method to get current user login data
     * **/
    public Token getTokenData(String username) { 	
    	return tokenRepository.findByUsername(username);	
    }

}
