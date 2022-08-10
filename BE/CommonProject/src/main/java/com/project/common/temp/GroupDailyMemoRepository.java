package com.project.common.temp;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupDailyMemoRepository extends JpaRepository<GroupDailyMemoEntity, Integer> {

	void deleteByGdmDate(Date gdmDate);

}
