package com.project.common.repository;

import com.project.common.entity.My.MyScheduleEntity;

import java.util.List;

public interface MyScheduleRepositoryCustom {
    List<MyScheduleEntity> findByUserSeqAndMyScheduleDate(int userSeq, int myScheduleDate);
    MyScheduleEntity findByUserSeqAndMyScheduleDateAndMyScheduleTime(int userSeq, int myScheduleDate, int myScheduleTime);
}
