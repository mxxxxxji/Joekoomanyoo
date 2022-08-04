package com.project.common.repository;

import com.project.common.entity.My.MyScheduleEntity;
import com.project.common.entity.My.QMyScheduleEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyScheduleRepositoryImpl implements MyScheduleRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MyScheduleEntity> findByUserSeqAndMyScheduleDate(int userSeq, int myScheduleDate) {
        QMyScheduleEntity qMyScheduleEntity = QMyScheduleEntity.myScheduleEntity;
        return jpaQueryFactory.select(qMyScheduleEntity).from(qMyScheduleEntity).where(qMyScheduleEntity.userSeq.eq(userSeq).and(qMyScheduleEntity.myScheduleDate.eq(myScheduleDate))).fetch();
    }
}
