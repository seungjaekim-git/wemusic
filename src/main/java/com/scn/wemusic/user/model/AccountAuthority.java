package com.scn.wemusic.user.model;


import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/*@Entity
@Slf4j
@Table(name ="account_authority")*/
public class AccountAuthority {

    @Id
    @Column()
    @GeneratedValue()
    private Long idx;

    private AccountEntity accountEntity;

    private String value;

    private String seq;
}
