package com.milestone.dto;

import com.milestone.model.Quest;
import com.milestone.model.QuestStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class QuestResponse {
    private Long id;
    private String title;
    private String description;
    private int xpReward;
    private int coinReward;
    private String category;
    private String difficulty;
    private String icon;
    private QuestStatus status;
    private LocalDate completedAt;
    private Integer progress;

    public static QuestResponse fromEntity(Quest quest) {
        QuestResponse dto = new QuestResponse();
        dto.setId(quest.getId());
        dto.setTitle(quest.getTitle());
        dto.setDescription(quest.getDescription());
        dto.setXpReward(quest.getXpReward());
        dto.setCoinReward(quest.getCoinReward());
        dto.setCategory(quest.getCategory());
        dto.setDifficulty(quest.getDifficulty());
        dto.setIcon(quest.getIcon());
        dto.setStatus(quest.getStatus());
        dto.setCompletedAt(quest.getCompletedAt());
        dto.setProgress(quest.getProgress());
        return dto;
    }
}
