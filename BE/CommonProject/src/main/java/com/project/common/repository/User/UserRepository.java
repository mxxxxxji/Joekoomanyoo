package com.project.common.repository.User;

import com.project.common.entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// entity와 연결해서 repository 생성해주면 jpa가 생성된 것이다.
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(String username);

    Optional<UserEntity> findOneByUserId(String userId);

    UserEntity findByUserNickname(String userNickname);

    UserEntity findByUserSeq(int userSeq);
}
