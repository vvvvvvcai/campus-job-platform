package com.campus.recommend.dto;

import lombok.Data;

@Data
public class AiRecommendResult {

    private Long jobId;

    private Double score;

    private String reason;
}
