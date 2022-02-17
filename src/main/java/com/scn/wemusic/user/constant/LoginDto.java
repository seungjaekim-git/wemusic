package com.scn.wemusic.user.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class LoginDto {

    @Getter
    @NoArgsConstructor
    public static class LoginReq implements Serializable {

        private String userId;

        private String password;

    }

    @Getter
    @NoArgsConstructor
    public static class LoginRes implements Serializable {


    }


}
