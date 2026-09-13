package com.milestone.service;

import com.milestone.dto.AchievementDto;
import com.milestone.model.Achievement;
import com.milestone.model.User;
import com.milestone.model.UserAchievement;
import com.milestone.repository.AchievementRepository;
import com.milestone.repository.UserAchievementRepository;
import com.milestone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository,
                              UserAchievementRepository userAchievementRepository,
                              UserRepository userRepository) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<AchievementDto> getUserAchievements() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Achievement> allAchievements = achievementRepository.findAll();
        List<UserAchievement> earned = userAchievementRepository.findByUserId(user.getId());
        
        Map<Long, LocalDate> earnedMap = earned.stream()
                .collect(Collectors.toMap(ua -> ua.getAchievement().getId(), UserAchievement::getEarnedAt));

        List<AchievementDto> dtos = new ArrayList<>();
        for (Achievement a : allAchievements) {
            AchievementDto dto = new AchievementDto();
            dto.setId(a.getId());
            dto.setKey(a.getKey());
            dto.setTitle(a.getTitle());
            dto.setDescription(a.getDescription());
            dto.setIcon(a.getIcon());
            if (earnedMap.containsKey(a.getId())) {
                dto.setEarned(true);
                dto.setEarnedAt(earnedMap.get(a.getId()));
            } else {
                dto.setEarned(false);
            }
            dtos.add(dto);
        }
        return dtos;
    }

    public List<AchievementDto> checkAndAward(User user) {
        List<Achievement> allAchievements = achievementRepository.findAll();
        List<AchievementDto> newlyUnlocked = new ArrayList<>();

        for (Achievement a : allAchievements) {
            if (!userAchievementRepository.existsByUserIdAndAchievementId(user.getId(), a.getId())) {
                boolean meetsTrigger = false;
                switch (a.getTriggerType()) {
                    case QUEST_COUNT:
                        meetsTrigger = user.getQuestsDone() >= a.getTriggerValue();
                        break;
                    case STREAK_DAYS:
                        meetsTrigger = user.getStreak() >= a.getTriggerValue();
                        break;
                    case LEVEL_REACHED:
                        meetsTrigger = user.getLevel() >= a.getTriggerValue();
                        break;
                    case XP_EARNED:
                        meetsTrigger = user.getXp() >= a.getTriggerValue(); // Simplified, normally total XP
                        break;
                    case COINS_EARNED:
                        meetsTrigger = user.getCoins() >= a.getTriggerValue(); // Simplified, normally total coins
                        break;
                }

                if (meetsTrigger) {
                    UserAchievement ua = new UserAchievement();
                    ua.setUser(user);
                    ua.setAchievement(a);
                    ua.setEarnedAt(LocalDate.now());
                    userAchievementRepository.save(ua);
                    
                    user.setAchievementCount(user.getAchievementCount() + 1);

                    AchievementDto dto = new AchievementDto();
                    dto.setId(a.getId());
                    dto.setKey(a.getKey());
                    dto.setTitle(a.getTitle());
                    dto.setDescription(a.getDescription());
                    dto.setIcon(a.getIcon());
                    dto.setEarned(true);
                    dto.setEarnedAt(ua.getEarnedAt());
                    newlyUnlocked.add(dto);
                }
            }
        }
        
        if (!newlyUnlocked.isEmpty()) {
            userRepository.save(user);
        }
        
        return newlyUnlocked;
    }
}
