package com.scn.wemusic.common.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@RequiredArgsConstructor
public enum OAuthProviderType {
    GOOGLE("G","구글"),
    FACEBOOK("F","페이스북"),
    NAVER("N","네이버"),
    KAKAO("K","카카오"),
    LOCAL("L","로컬");
    ;

    private final String code;
    private final String desc;

    public String getLegacyCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static OAuthProviderType stringToEnum(String value) {
        if (value == null || StringUtils.isEmpty(value)) return null;
        for (OAuthProviderType snsType : OAuthProviderType.values()) {
            if (value.equals(snsType.name())
                    || value.equalsIgnoreCase(snsType.getLegacyCode())
            ) {
                return snsType;
            }
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class OAuthProviderTypeConverter implements AttributeConverter<OAuthProviderType, String> {

        @Override
        public String convertToDatabaseColumn(OAuthProviderType snsType) {
            return snsType == null ?  null : snsType.getLegacyCode();
        }

        @Override
        public OAuthProviderType convertToEntityAttribute(String s) {
            return OAuthProviderType.stringToEnum(s);
        }
    }
}