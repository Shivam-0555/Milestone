package com.milestone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeatmapEntry {
    private String date;
    private long count;
}
