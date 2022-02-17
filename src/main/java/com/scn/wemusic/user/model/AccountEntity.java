package com.scn.wemusic.user.model;

import com.scn.wemusic.user.constant.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/*@Slf4j
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account", schema = "wemusic")*/
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long accountIdx;

    @Column
    @ManyToOne()
    @JoinColumn(name = "owner", updatable = false)
    private UserEntity accountOwner;

    @Column()
    private AccountType accountType;



}
