package com.milestone.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AchievementDto {
    private Long id;
    private String key;
    private String title;
    private String description;
    private String icon;
    private boolean earned;
    private LocalDate earnedAt;
}
