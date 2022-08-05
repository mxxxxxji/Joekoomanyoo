package com.project.common.repository.Group;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupScheduleEntity;


public interface GroupScheduleRepository extends JpaRepository<GroupScheduleEntity, Integer> {

	void deleteByGsDateTime(long gsDateTime);

}
