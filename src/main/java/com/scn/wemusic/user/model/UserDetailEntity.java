package com.scn.wemusic.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class UserDetailEntity implements Serializable {

    @Id
    @Column()
    private Long idx;

    @Column()
    private String fistName;

    @Column()
    private String lastName;

    @Column()
    private String emailAddress;

    @Column()
    private String phoneNumber;

    @Column()
    private String zipCode;

    @Column()
    private String address;

    @Column()
    private String address2;

}
