package com.scn.wemusic.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.scn.wemusic.common.constant.OAuthProviderType;
import com.scn.wemusic.common.constant.UserRoleType;
import com.scn.wemusic.common.constant.YesNoType;
import com.scn.wemusic.common.event.BaseTimeEntity;
import com.scn.wemusic.user.item.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BaseTimeEntity implements Serializable, UserDetails {

    @JsonIgnore
    @Id
    @Column(name = "user_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "user_name", length = 100)
    @NotNull
    private String userName;

    @JsonIgnore
    @Column(length = 400, name = "password")
    private String password;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "profile_image_url", length = 512)
    @NotNull
    private String profileImageUrl;

    @Column(name = "provider_type", length = 20)
    @NotNull
    private OAuthProviderType providerType;

    @Column(name = "user_role_type", length = 20)
    @NotNull
    private UserRoleType roleType;

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
    @Convert(converter = YesNoType.YesNoTypeAttributeConverter.class)
    @Column(name = "is_sms_yn", columnDefinition = "CHAR(1)", nullable = false)
    private YesNoType isSmsYn;

    @Expose
    @Convert(converter = YesNoType.YesNoTypeAttributeConverter.class)
    @Column(name = "is_push_yn", columnDefinition = "CHAR(1)", nullable = false)
    private YesNoType isPushYn;

    @Expose
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

    @Builder
    public UserEntity(OAuth2UserInfo userInfo, OAuthProviderType providerType) {
        this.userId = userInfo.getId();
        this.userName = userInfo.getName();
        this.email = userInfo.getEmail();
        this.profileImageUrl = userInfo.getImageUrl();
        this.providerType = providerType;
        this.roleType = UserRoleType.USER;
        this.lastLoginDate = new Date();
        this.registerDate = new Date();
        this.passwordFailCount = 0;
        this.changePwDate = new Date();
        this.isSmsYn = YesNoType.NO;
        this.isPushYn = YesNoType.NO;
        this.isEmailYn = YesNoType.NO;
    }

    public void setUsername(String name) {
        this.userName = name;
    }

    public void setProfileImageUrl(String imageUrl) {
        this.profileImageUrl = imageUrl;
    }
}
