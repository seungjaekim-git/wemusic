package com.scn.wemusic.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum UserRoleType {
    USER("ROLE_USER", "일반 사용자 권한"),
    ADMIN("ROLE_ADMIN", "관리자 권한"),
    GUEST("GUEST", "게스트 권한");

    private final String code;
    private final String displayName;

    public String getLegacyCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserRoleType of(String code) {
        return Arrays.stream(UserRoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }

    public static UserRoleType stringToEnum(String value) {
        if (value == null || StringUtils.isEmpty(value)) return null;
        for (UserRoleType snsType : UserRoleType.values()) {
            if (value.equals(snsType.name())
                    || value.equalsIgnoreCase(snsType.getLegacyCode())
            ) {
                return snsType;
            }
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class UserRoleTypeConverter implements AttributeConverter<UserRoleType, String> {

        @Override
        public String convertToDatabaseColumn(UserRoleType snsType) {
            return snsType == null ?  null : snsType.getLegacyCode();
        }

        @Override
        public UserRoleType convertToEntityAttribute(String s) {
            return UserRoleType.stringToEnum(s);
        }
    }
}
