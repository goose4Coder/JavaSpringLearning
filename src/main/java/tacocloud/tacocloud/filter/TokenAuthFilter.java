package tacocloud.tacocloud.filter;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tacocloud.tacocloud.dto.UserDto;
import tacocloud.tacocloud.service.AuthService;
import tacocloud.tacocloud.service.UserService;
import tacocloud.tacocloud.utils.JwtUtil;

import java.io.IOException;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            System.out.println("Try");
            String jwt = parseJwt(request);
//            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            System.out.println("jwt");
            if (jwt != null) {

                String username = jwtUtils.extractUsername(jwt);
                System.out.println("usr");
                UserDetails userDetails = authService.getDetailsByUsername(username);
                System.out.println("User got");
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                System.out.println("Data done");
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("Details set");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e);
        }
        filterChain.doFilter(request, response);
    }
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
