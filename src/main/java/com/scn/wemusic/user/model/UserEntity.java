package com.scn.wemusic.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import com.scn.wemusic.common.constant.SNSType;
import com.scn.wemusic.common.constant.YesNoType;
import com.scn.wemusic.common.event.BaseEntity;
import com.scn.wemusic.user.constant.AccountStatusType;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BaseEntity implements Serializable, UserDetails {

    @Id
    @GeneratedValue()
    @Column(name = "idx")
    private Long idx;

    @Column(name = "accountId")
    private String userId;

    @Column(length = 400, name = "password")
    private String password;

    @Column(name = "status")
    private AccountStatusType accountStatusType;

    @Column(name = "last_login_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date lastLoginDate;

    @Column(name = "register_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date registerDate;

    @Expose
    @Column(name = "password_fail_count", columnDefinition = "INT(2) DEFAULT 0")
    private Integer passwordFailCount;

    @Expose
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "change_pw_date", columnDefinition = "DATETIME")
    protected Date changePwDate;

    @Expose
    @ColumnDefault("N")
    @Convert(converter = YesNoType.YesNoTypeAttributeConverter.class)
    @Column(name = "is_sms_yn", columnDefinition = "CHAR(1)", nullable = false)
    private YesNoType isSmsYn;

    @Expose
    @ColumnDefault("N")
    @Convert(converter = YesNoType.YesNoTypeAttributeConverter.class)
    @Column(name = "is_push_yn", columnDefinition = "CHAR(1)", nullable = false)
    private YesNoType isPushYn;

    @Expose
    @ColumnDefault("N")
    @Convert(converter = YesNoType.YesNoTypeAttributeConverter.class)
    @Column(name = "is_email_yn", columnDefinition = "CHAR(1)", nullable = false)
    private YesNoType isEmailYn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
