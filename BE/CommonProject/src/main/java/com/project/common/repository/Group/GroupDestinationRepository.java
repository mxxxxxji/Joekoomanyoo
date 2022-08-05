package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Heritage.HeritageEntity;


public interface GroupDestinationRepository extends JpaRepository<GroupDestinationEntity, Integer> {

	void deleteByHeritageSeq(int heritageSeq);




}
