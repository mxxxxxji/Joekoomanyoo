package com.project.common.repository.Group;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.common.entity.Group.GroupScheduleEntity;


public interface GroupScheduleRepository extends JpaRepository<GroupScheduleEntity, Integer> {

	void deleteByGsDateTime(Date gsDateTime);

}
