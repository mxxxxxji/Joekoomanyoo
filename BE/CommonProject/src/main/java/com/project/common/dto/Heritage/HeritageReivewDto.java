package com.project.common.dto.Heritage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeritageReivewDto {
    private int heritageReviewSeq;
    private int userSeq;
    private int heritageSeq;
    private String heritageReviewText;
    private LocalDateTime heritageReviewRegistedAt;
}
