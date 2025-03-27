package com.amcamp.global.security;

import com.amcamp.domain.auth.application.JwtTokenService;
import com.amcamp.domain.auth.dto.AccessTokenDto;
import com.amcamp.domain.auth.dto.RefreshTokenDto;
import com.amcamp.domain.member.domain.MemberRole;
import com.amcamp.global.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessTokenHeaderValue = extractAccessTokenFromHeader(request);
        String refreshTokenCookieValue = extractRefreshTokenFromCookie(request);

        if (accessTokenHeaderValue != null) {
            AccessTokenDto accessTokenDto =
                    jwtTokenService.retrieveAccessToken(accessTokenHeaderValue);

            // AT가 유효하면 통과
            if (accessTokenDto != null) {
                setAuthenticationToken(accessTokenDto.memberId(), accessTokenDto.role());

                filterChain.doFilter(request, response);
                return;
            }
        }

        // AT가 유효하지 않다면 RT 파싱
        RefreshTokenDto refreshTokenDto = jwtTokenService.retrieveRefreshToken(refreshTokenCookieValue);

        // RT가 유효하면 AT, RT 재발급
        if (refreshTokenDto != null) {
            AccessTokenDto reissueAccessTokenDto =
                    jwtTokenService.reissueAccessTokenIfExpired(accessTokenHeaderValue);
            RefreshTokenDto reissueRefreshTokenDto =
                    jwtTokenService.createRefreshTokenDto(refreshTokenDto.memberId());

            HttpHeaders headers =
                    cookieUtil.generateRefreshTokenCookie(reissueRefreshTokenDto.refreshTokenValue());

            response.addHeader(HttpHeaders.AUTHORIZATION,
                    "Bearer " + reissueAccessTokenDto.accessTokenValue());
            response.addHeader(HttpHeaders.SET_COOKIE, headers.getFirst(HttpHeaders.SET_COOKIE));
        }

        // AT, RT가 모두 만료된 경우 실패
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToken(Long memberId, MemberRole role) {
        UserDetails userDetails = new PrincipalDetails(memberId, role);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }

        return null;
    }

    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");

        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }
}