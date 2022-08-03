package com.project.common.repository;

import com.project.common.entity.HeritageScrapEntity;

public interface HeritageScrapRepositoryCustom {
    boolean deleteByUserSeqAndHeritageSeq(int userSeq, int heritageSeq);

    HeritageScrapEntity findByUserSeqAndHeritageSeq(int userSeq, int heritageSeq);
}
