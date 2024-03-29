package com.project.electricityBillManagement.jwt;

import com.project.electricityBillManagement.enumeration.Role;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    String token;


    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith(("Bearer "))){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        token = jwt;
        userEmail = jwtService.extractUserName(jwt);// todo extract the userEmail from jwt

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    public boolean IsAdmin(){
        Role role = jwtService.getRoleFromToken(token);
        return role == Role.admin;
    }

    public String getUserName(){
        return jwtService.extractUserName(token);
    }

    public boolean checkTokenValid(String jwtuser){
//        jwt = authHeader.substring(7);
        log.info("filter inside :: " + jwtuser);
        token = jwtuser;
        String userEmail = jwtService.extractUserName(jwtuser);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if(jwtService.isTokenValid(token,userDetails)){
            return true;
        }
        return false;
    }


}
