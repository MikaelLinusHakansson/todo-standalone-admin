package com.example.todoappstandaloneadmin.security.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    /**
     * Performs the filter internally, checking and validating the JWT token
     *
     * @param  request       the HTTP servlet request
     * @param  response      the HTTP servlet response
     * @param  filterChain   the filter chain
     * @throws ServletException  if a servlet-specific error occurs
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getJWTFromRequest(request);

        try {
            if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
                String username = jwtGenerator.getUserNameFromJWT(token);

                UserDetails userDetails = customerUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves JWT token from the HTTP request header.
     *
     * @param  request   the HTTP servlet request
     * @return          the JWT token extracted from the request
     */
    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
