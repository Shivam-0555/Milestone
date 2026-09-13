package com.milestone.service;

import com.milestone.dto.ShopItemDto;
import com.milestone.model.ShopItem;
import com.milestone.model.User;
import com.milestone.model.UserInventory;
import com.milestone.repository.ShopItemRepository;
import com.milestone.repository.UserInventoryRepository;
import com.milestone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopItemRepository shopItemRepository;
    private final UserInventoryRepository userInventoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShopService(ShopItemRepository shopItemRepository,
                       UserInventoryRepository userInventoryRepository,
                       UserRepository userRepository) {
        this.shopItemRepository = shopItemRepository;
        this.userInventoryRepository = userInventoryRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<ShopItemDto> getAllItems() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<ShopItem> allItems = shopItemRepository.findAll();
        List<UserInventory> inventory = userInventoryRepository.findByUserId(user.getId());
        
        Map<Long, Boolean> ownedMap = inventory.stream()
                .collect(Collectors.toMap(ui -> ui.getShopItem().getId(), ui -> true));

        List<ShopItemDto> dtos = new ArrayList<>();
        for (ShopItem item : allItems) {
            ShopItemDto dto = new ShopItemDto();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setIcon(item.getIcon());
            dto.setCost(item.getCost());
            dto.setCategory(item.getCategory());
            dto.setOwned(ownedMap.containsKey(item.getId()));
            dtos.add(dto);
        }
        return dtos;
    }

    public void buyItem(Long itemId) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        ShopItem item = shopItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        
        if (userInventoryRepository.existsByUserIdAndShopItemId(user.getId(), itemId)) {
            throw new IllegalArgumentException("Item already owned");
        }
        
        if (user.getCoins() < item.getCost()) {
            throw new IllegalArgumentException("Not enough coins");
        }
        
        user.setCoins(user.getCoins() - item.getCost());
        userRepository.save(user);
        
        UserInventory ui = new UserInventory();
        ui.setUser(user);
        ui.setShopItem(item);
        ui.setPurchasedAt(LocalDateTime.now());
        userInventoryRepository.save(ui);
    }
    
    public List<ShopItemDto> getUserInventory() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
                
        List<UserInventory> inventory = userInventoryRepository.findByUserId(user.getId());
        return inventory.stream().map(ui -> {
            ShopItem item = ui.getShopItem();
            ShopItemDto dto = new ShopItemDto();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setIcon(item.getIcon());
            dto.setCost(item.getCost());
            dto.setCategory(item.getCategory());
            dto.setOwned(true);
            return dto;
        }).collect(Collectors.toList());
    }
}
