package com.scn.wemusic.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_refresh_token")
public class UserRefreshToken {

    @JsonIgnore
    @Id
    @Column(name = "refresh_token_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenSeq;

    @Column(name = "user_id", length = 64, unique = true)
    @NotNull
    private String userId;

    @Column(name = "refresh_token", length = 256)
    @NotNull
    private String refreshToken;

    public UserRefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
