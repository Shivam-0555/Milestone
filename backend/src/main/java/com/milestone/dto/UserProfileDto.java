package com.milestone.dto;

import com.milestone.model.User;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    
    // RPG Stats
    private Integer level;
    private Integer xp;
    private Integer xpToNext;
    private Integer coins;
    private Integer streak;
    private Integer bestStreak;
    private String className;
    
    private Integer intelligence;
    private Integer strength;
    private Integer discipline;
    private Integer vitality;
    
    private Integer questsDone;
    private Integer achievementCount;
    private LocalDate lastActiveDate;

    public static UserProfileDto fromEntity(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        dto.setEmail(user.getEmail());
        
        dto.setLevel(user.getLevel());
        dto.setXp(user.getXp());
        dto.setXpToNext(user.getXpToNext());
        dto.setCoins(user.getCoins());
        dto.setStreak(user.getStreak());
        dto.setBestStreak(user.getBestStreak());
        dto.setClassName(user.getClassName());
        
        dto.setIntelligence(user.getIntelligence());
        dto.setStrength(user.getStrength());
        dto.setDiscipline(user.getDiscipline());
        dto.setVitality(user.getVitality());
        
        dto.setQuestsDone(user.getQuestsDone());
        dto.setAchievementCount(user.getAchievementCount());
        dto.setLastActiveDate(user.getLastActiveDate());
        
        return dto;
    }
}
