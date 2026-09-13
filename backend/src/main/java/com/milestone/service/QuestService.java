package com.milestone.service;

import com.milestone.dto.QuestRequest;
import com.milestone.model.Quest;
import com.milestone.model.User;
import com.milestone.repository.QuestRepository;
import com.milestone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestService {

    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final AchievementService achievementService;

    @Autowired
    public QuestService(QuestRepository questRepository, UserRepository userRepository, AchievementService achievementService) {
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.achievementService = achievementService;
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Quest createQuest(QuestRequest request) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Quest quest = new Quest();
        quest.setTitle(request.getTitle());
        quest.setDescription(request.getDescription());
        quest.setXpReward(request.getXpReward());
        quest.setCoinReward(request.getCoinReward());
        quest.setCategory(request.getCategory());
        quest.setDifficulty(request.getDifficulty());
        quest.setIcon(request.getIcon());
        quest.setOwner(user);
        return questRepository.save(quest);
    }

    public List<Quest> getUserQuests() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return questRepository.findByOwnerId(user.getId());
    }

    public Quest getQuest(Long id) {
        String username = getCurrentUsername();
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quest not found"));
        if (!quest.getOwner().getUsername().equals(username)) {
            throw new IllegalArgumentException("Not authorized");
        }
        return quest;
    }

    public Quest updateQuest(Long id, QuestRequest request) {
        Quest quest = getQuest(id);
        quest.setTitle(request.getTitle());
        quest.setDescription(request.getDescription());
        quest.setXpReward(request.getXpReward());
        quest.setCoinReward(request.getCoinReward());
        return questRepository.save(quest);
    }

    public void deleteQuest(Long id) {
        Quest quest = getQuest(id);
        questRepository.delete(quest);
    }

    public com.milestone.dto.QuestCompletionResponse completeQuest(Long id) {
        Quest quest = getQuest(id);
        if (quest.getStatus() == com.milestone.model.QuestStatus.COMPLETED) {
            throw new IllegalArgumentException("Quest already completed");
        }
        
        quest.setStatus(com.milestone.model.QuestStatus.COMPLETED);
        quest.setCompletedAt(java.time.LocalDate.now());
        quest.setProgress(100);
        questRepository.save(quest);
        
        User user = quest.getOwner();
        user.setCoins(user.getCoins() + quest.getCoinReward());
        user.setXp(user.getXp() + quest.getXpReward());
        user.setQuestsDone(user.getQuestsDone() + 1);
        
        boolean leveledUp = false;
        while (user.getXp() >= user.getXpToNext()) {
            user.setXp(user.getXp() - user.getXpToNext());
            user.setLevel(user.getLevel() + 1);
            user.setXpToNext(user.getLevel() * 150);
            leveledUp = true;
        }
        
        boolean streakUpdated = false;
        java.time.LocalDate today = java.time.LocalDate.now();
        if (user.getLastActiveDate() == null || user.getLastActiveDate().isBefore(today.minusDays(1))) {
            user.setStreak(1);
            streakUpdated = true;
        } else if (user.getLastActiveDate().equals(today.minusDays(1))) {
            user.setStreak(user.getStreak() + 1);
            if (user.getStreak() > user.getBestStreak()) {
                user.setBestStreak(user.getStreak());
            }
            streakUpdated = true;
        }
        user.setLastActiveDate(today);
        
        userRepository.save(user);
        
        List<com.milestone.dto.AchievementDto> unlocked = achievementService.checkAndAward(user);
        
        com.milestone.dto.QuestCompletionResponse response = new com.milestone.dto.QuestCompletionResponse();
        response.setQuestId(quest.getId());
        response.setXpGained(quest.getXpReward());
        response.setCoinsGained(quest.getCoinReward());
        response.setLeveledUp(leveledUp);
        response.setNewLevel(user.getLevel());
        response.setNewXp(user.getXp());
        response.setNewXpToNext(user.getXpToNext());
        response.setStreakUpdated(streakUpdated);
        response.setCurrentStreak(user.getStreak());
        response.setAchievementsUnlocked(unlocked);
        
        return response;
    }

    public List<com.milestone.dto.HeatmapEntry> getHeatmap() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        List<com.milestone.dto.HeatmapEntry> entries = new java.util.ArrayList<>();
        java.time.LocalDate end = java.time.LocalDate.now();
        java.time.LocalDate start = end.minusDays(364);
        
        for (java.time.LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            long count = questRepository.countByOwnerIdAndCompletedAt(user.getId(), d);
            entries.add(new com.milestone.dto.HeatmapEntry(d.toString(), count));
        }
        return entries;
    }
}
