package com.scn.wemusic.user.repository;

import com.scn.wemusic.user.model.UserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {
}
