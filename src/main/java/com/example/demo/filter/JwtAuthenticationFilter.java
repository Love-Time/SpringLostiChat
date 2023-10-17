package com.example.demo.filter;

import com.example.demo.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeaders = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (Objects.equals(request.getRequestURI(), "/chat")){
            String tokenParam = request.getParameter("token");
            if (Objects.equals(tokenParam, "")){
                System.out.println("HEREEEEEE");
                Writer writer = response.getWriter();
                response.setStatus(403);
                writer.write("{'errors': 'token should not be null'}");
                writer.flush();
                return;
            }
            authHeaders = "Bearer " + tokenParam;
        }

        if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwt = authHeaders.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)){
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
            filterChain.doFilter(request, response);


        } catch (ExpiredJwtException e) {
            Writer writer = response.getWriter();
            response.setStatus(403);
            writer.write("{'errors': 'token expired'}");
            writer.flush();

        } catch (JwtException | UsernameNotFoundException e)
        {
            Writer writer = response.getWriter();
            response.setStatus(403);
            writer.write("{'errors': 'token invalid'}");
            writer.flush();

        }

    }
}
