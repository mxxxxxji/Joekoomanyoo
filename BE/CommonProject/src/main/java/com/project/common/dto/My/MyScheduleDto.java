package com.project.common.dto.My;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyScheduleDto {
    private int myScheduleSeq;
    private int myScheduleDate;
    private int myScheduleTime;
    private String myScheduleContent;
    private String myScheduleRegistedAt;
    private String myScheduleUpdatedAt;
    private int userSeq;
}
