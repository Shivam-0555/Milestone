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

    @Autowired
    public QuestService(QuestRepository questRepository, UserRepository userRepository) {
        this.questRepository = questRepository;
        this.userRepository = userRepository;
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
}
