package com.scn.wemusic.common.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@RequiredArgsConstructor
public enum SNSType {
    Google("Y", "긍정"),
    NO("N","부정"),
    ;

    private final String code;
    private final String desc;

    public String getLegacyCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static SNSType stringToEnum(String value) {
        if (value == null || StringUtils.isEmpty(value)) return null;
        for (SNSType snsType : SNSType.values()) {
            if (value.equals(snsType.name())
                    || value.equalsIgnoreCase(snsType.getLegacyCode())
            ) {
                return snsType;
            }
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class SNSTypeConverter implements AttributeConverter<SNSType, String> {

        @Override
        public String convertToDatabaseColumn(SNSType snsType) {
            return snsType == null ?  null : snsType.getLegacyCode();
        }

        @Override
        public SNSType convertToEntityAttribute(String s) {
            return SNSType.stringToEnum(s);
        }
    }
}
