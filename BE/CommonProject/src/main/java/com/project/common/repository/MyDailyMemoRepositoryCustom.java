package com.project.common.repository;

import com.project.common.entity.My.MyDailyMemoEntity;

public interface MyDailyMemoRepositoryCustom {
    MyDailyMemoEntity findByUserSeqAndMyDailyMemoDate(int userSeq, int myDailyMemoDate);
}
