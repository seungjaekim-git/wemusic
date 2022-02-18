package com.scn.wemusic.user.item;

import lombok.Getter;
import lombok.Setter;

public class LoginDto {

    @Getter
    @Setter
    public static class LoginReqDto{

        private String id;
        private String password;

    }
}
