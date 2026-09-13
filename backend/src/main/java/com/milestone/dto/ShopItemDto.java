package com.milestone.dto;

import lombok.Data;

@Data
public class ShopItemDto {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private int cost;
    private String category;
    private boolean owned;
}
