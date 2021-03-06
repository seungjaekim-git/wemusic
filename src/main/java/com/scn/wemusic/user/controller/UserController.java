package com.scn.wemusic.user.controller;

import com.scn.wemusic.security.token.AuthToken;
import com.scn.wemusic.security.token.AuthTokenProvider;
import com.scn.wemusic.user.item.AppProperties;
import com.scn.wemusic.user.item.LoginDto;
import com.scn.wemusic.user.item.UserPrincipal;
import com.scn.wemusic.user.model.UserRefreshToken;
import com.scn.wemusic.user.repository.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@CrossOrigin(origins = {"*"}, exposedHeaders = {"Content-Disposition","content-disposition"}, allowedHeaders = {"*"})
public class UserController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    public ResponseEntity login(
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute LoginDto.LoginReqDto req
            ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getId(),
                        req.getPassword()
                )
        );

        String userId = req.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token ?????? DB ??????
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // ?????? ?????? ?????? ??????
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB??? refresh ?????? ????????????
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        return ResponseEntity.ok().body("token : " + accessToken.getToken());
    }

    /*@GetMapping("/refresh")
    public ResponseEntity refreshToken (HttpServletRequest request, HttpServletResponse response) {
        // access token ??????
        private final static String HEADER_AUTHORIZATION = "Authorization";
        private final static String TOKEN_PREFIX = "Bearer ";

        String accessToken = request.getHeader(HEADER_AUTHORIZATION).substring(TOKEN_PREFIX.length());
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ResponseEntity.badRequest().build();
        }

        // expired access token ?????? ??????
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ResponseEntity.badRequest().build();
        }

        String userId = claims.getSubject();
        UserRoleType roleType = UserRoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (authRefreshToken.validate()) {
            return return ResponseEntity.badRequest().build();
        }

        // userId refresh token ?????? DB ??????
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            return return ResponseEntity.badRequest().build();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh ?????? ????????? 3??? ????????? ?????? ??????, refresh ?????? ??????
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh ?????? ??????
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB??? refresh ?????? ????????????
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            *//*int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        *//*}

        return  ResponseEntity.ok().body(newAccessToken.getToken());
    }
*/


}
