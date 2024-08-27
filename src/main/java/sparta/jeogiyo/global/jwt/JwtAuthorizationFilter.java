package sparta.jeogiyo.global.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import sparta.jeogiyo.global.response.ErrorCode;
import sparta.jeogiyo.global.response.ResponseMapper;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = jwtUtil.getJwtFromHeader(request);
//            if (token.isBlank()) {
//                token = jwtUtil.getJwtFromCookie(request);
//            }
            if (!token.isBlank()) {
                if (!jwtUtil.validateToken(token)) {
                    log.error("JWT 토큰 검증 실패: {}", token);
                    ResponseMapper.writeErrorCodeResponse(response, ErrorCode.INVALID_JWT_TOKEN);
                    return;
                }

                Claims info = jwtUtil.getUserInfoFromToken(token);

                try {
                    setAuthentication(info.getSubject());
                } catch (UsernameNotFoundException e) {
                    ResponseMapper.writeErrorCodeResponse(response, ErrorCode.USER_NOT_FOUND);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }
}
