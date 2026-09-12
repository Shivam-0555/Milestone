package com.milestone.dto;

public class QuestRequest {
    private String title;
    private String description;
    private int xpReward;
    private int coinReward;

    // getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getXpReward() { return xpReward; }
    public void setXpReward(int xpReward) { this.xpReward = xpReward; }
    public int getCoinReward() { return coinReward; }
    public void setCoinReward(int coinReward) { this.coinReward = coinReward; }
}
