package com.scn.wemusic.common.constant;


import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@RequiredArgsConstructor
public enum YesNoType {
    YES("Y", "긍정"),
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

    public static YesNoType stringToEnum(String value) {
        if (value == null || StringUtils.isEmpty(value)) return null;
        for (YesNoType yesNoType : YesNoType.values()) {
            if (value.equals(yesNoType.name())
                    || value.equalsIgnoreCase(yesNoType.getLegacyCode())
            ) {
                return yesNoType;
            }
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class YesNoTypeAttributeConverter implements AttributeConverter<YesNoType, String> {

        @Override
        public String convertToDatabaseColumn(YesNoType yesNoType) {
            return yesNoType == null ?  null : yesNoType.getLegacyCode();
        }

        @Override
        public YesNoType convertToEntityAttribute(String s) {
            return YesNoType.stringToEnum(s);
        }
    }

}
