package com.scn.wemusic.user.service;

import com.scn.wemusic.common.constant.OAuthProviderType;
import com.scn.wemusic.user.item.OAuth2UserInfo;
import com.scn.wemusic.user.item.OAuth2UserInfoFactory;
import com.scn.wemusic.user.item.UserPrincipal;
import com.scn.wemusic.user.model.UserEntity;
import com.scn.wemusic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        OAuthProviderType providerType = OAuthProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        UserEntity savedUser = userRepository.findByUserId(userInfo.getId()).orElse(null);

        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                try {
                    throw new Exception(
                            "Looks like you're signed up with " + providerType +
                                    " account. Please use your " + savedUser.getProviderType() + " account to login."
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private UserEntity createUser(OAuth2UserInfo userInfo, OAuthProviderType providerType) {
        Date now = new Date();
        UserEntity user = UserEntity.builder().userInfo(userInfo).providerType(providerType).build();
        return userRepository.saveAndFlush(user);
    }

    private UserEntity updateUser(UserEntity user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.setUsername(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            user.setProfileImageUrl(userInfo.getImageUrl());
        }

        return user;
    }
}
