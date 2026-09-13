package com.milestone.repository;

import com.milestone.model.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {
    List<UserInventory> findByUserId(Long userId);
    boolean existsByUserIdAndShopItemId(Long userId, Long shopItemId);
}
