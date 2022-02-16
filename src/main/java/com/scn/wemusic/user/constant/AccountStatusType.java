package com.scn.wemusic.user.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public enum AccountStatusType {
    ACTIVE("A", "활성화"),
    PAUSE("P","임시정지"),
    DORMANT("D","휴면 계정"),
    BLACK("B","블랙 계정"),
    PERMANENT_SUSPENSION("S","영구정지"),
    LEAVE("L","탈퇴 계정")
    ;

    private final String code;
    private final String desc;

    public String getLegacyCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static AccountStatusType stringToEnum(String value) {
        if (value == null || StringUtils.isEmpty(value)) return null;
        for (AccountStatusType accountStatusType : AccountStatusType.values()) {
            if (value.equals(accountStatusType.name())
                    || value.equalsIgnoreCase(accountStatusType.getLegacyCode())
            ) {
                return accountStatusType;
            }
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class AccountStatusTypeConverter implements AttributeConverter<AccountStatusType, String> {
        @Override
        public String convertToDatabaseColumn(AccountStatusType accountStatusType) {
            return accountStatusType == null ?  null : accountStatusType.getLegacyCode();
        }

        @Override
        public AccountStatusType convertToEntityAttribute(String s) {
            return AccountStatusType.stringToEnum(s);
        }
    }
}
