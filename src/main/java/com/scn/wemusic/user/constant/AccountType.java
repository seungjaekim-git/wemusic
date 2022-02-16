package com.scn.wemusic.user.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@RequiredArgsConstructor
public enum AccountType {

    CUSTOMER("C", "일반 회원"),
    TECHNICAL("T","전문인"),
    PARTNERS("P","입점업체")
    ;

    private final String code;
    private final String desc;

    public String getLegacyCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static AccountType stringToEnum(String value) {
        if (value == null || StringUtils.isEmpty(value)) return null;
        for (AccountType accountType : AccountType.values()) {
            if (value.equals(accountType.name())
                    || value.equalsIgnoreCase(accountType.getLegacyCode())
            ) {
                return accountType;
            }
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class AccountTypeAttributeConverter implements AttributeConverter<AccountType, String> {
        @Override
        public String convertToDatabaseColumn(AccountType accountType) {
            return accountType == null ?  null : accountType.getLegacyCode();
        }

        @Override
        public AccountType convertToEntityAttribute(String s) {
            return AccountType.stringToEnum(s);
        }
    }

}
