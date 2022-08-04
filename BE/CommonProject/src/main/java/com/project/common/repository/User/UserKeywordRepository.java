package com.project.common.repository.User;

import com.project.common.entity.User.UserKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeywordEntity, Integer> {

    UserKeywordEntity findByUserSeq(int userSeq);

    List<UserKeywordEntity> findAllByUserSeq(int userSeq);

    UserKeywordEntity findByMyKeywordSeq(int myKeywordSeq);

    void deleteByMyKeywordSeq(int myKeywordSeq);
}
