package com.scn.wemusic.user.constant;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class registerReq implements Serializable {

        private String name;

        private String phoneNumber;

        private String emailAddress;

    }

    @Getter
    @NoArgsConstructor
    public static class registerRes implements Serializable {



    }


}
