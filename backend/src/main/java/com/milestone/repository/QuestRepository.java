package com.milestone.repository;

import com.milestone.model.Quest;
import com.milestone.model.QuestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByOwnerId(Long ownerId);
    List<Quest> findByOwnerIdAndStatus(Long ownerId, QuestStatus status);
    long countByOwnerIdAndCompletedAt(Long ownerId, LocalDate date);
}
