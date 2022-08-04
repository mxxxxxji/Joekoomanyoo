package com.project.common.repository;

import com.project.common.entity.MyDailyMemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyDailyMemoRepository extends JpaRepository<MyDailyMemoEntity, Integer> {

}
