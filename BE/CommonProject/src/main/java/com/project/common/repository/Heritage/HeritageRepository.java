package com.project.common.repository.Heritage;

import com.project.common.entity.Heritage.HeritageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeritageRepository extends JpaRepository<HeritageEntity, Integer> {

    List<HeritageEntity> findAll();

    HeritageEntity findByHeritageSeq(int heritageSeq);

}
