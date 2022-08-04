package com.project.common.repository;

import com.project.common.entity.MyDailyMemoEntity;

public interface MyDailyMemoRepositoryCustom {
    MyDailyMemoEntity findByUserSeqAndMyDailyMemoDate(int userSeq, int myDailyMemoDate);
}
