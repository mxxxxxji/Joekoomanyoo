package com.project.common.dto.MyDailyMemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyDailyMemoDto {
    private int myDailyMemoSeq;
    private int myDailyMemoDate;
    private LocalDateTime myDailyMemoRegistedAt;
    private LocalDateTime myDailyMemoUpdatedAt;
    private String myDailyMemo;
    private int userSeq;
}
