package com.scn.wemusic.user.item;

import com.scn.wemusic.common.constant.OAuthProviderType;

import java.security.AuthProvider;
import java.util.Map;


public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(OAuthProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            //case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            //case NAVER: return new NaverOAuth2UserInfo(attributes);
            //case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
