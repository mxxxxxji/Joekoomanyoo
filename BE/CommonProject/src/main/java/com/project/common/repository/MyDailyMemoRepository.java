package com.project.common.repository;

import com.project.common.entity.My.MyDailyMemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyDailyMemoRepository extends JpaRepository<MyDailyMemoEntity, Integer> {

    MyDailyMemoEntity findByMyDailyMemoSeq(int myDailyMemoSeq);

    void deleteByMyDailyMemoSeq(int myDailyMemoSeq);
}
