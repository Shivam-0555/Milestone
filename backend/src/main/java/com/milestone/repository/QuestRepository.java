package com.milestone.repository;

import com.milestone.model.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByOwnerId(Long ownerId);
}
