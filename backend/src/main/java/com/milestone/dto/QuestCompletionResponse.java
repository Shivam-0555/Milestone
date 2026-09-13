package com.milestone.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestCompletionResponse {
    private Long questId;
    private int xpGained;
    private int coinsGained;
    private boolean leveledUp;
    private int newLevel;
    private int newXp;
    private int newXpToNext;
    private List<AchievementDto> achievementsUnlocked = new ArrayList<>();
    private boolean streakUpdated;
    private int currentStreak;
}
