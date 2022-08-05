package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupDailyMemoEntity;


public interface GroupDailyMemoRepository extends JpaRepository<GroupDailyMemoEntity, Long> {

	void deleteByGdmDate(int gdmDate);

}
