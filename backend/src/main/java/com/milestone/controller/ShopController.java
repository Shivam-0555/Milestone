package com.milestone.controller;

import com.milestone.dto.ShopItemDto;
import com.milestone.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/shop")
    public ResponseEntity<List<ShopItemDto>> getAllItems() {
        return ResponseEntity.ok(shopService.getAllItems());
    }

    @PostMapping("/shop/{itemId}/buy")
    public ResponseEntity<Void> buyItem(@PathVariable Long itemId) {
        shopService.buyItem(itemId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/inventory")
    public ResponseEntity<List<ShopItemDto>> getInventory() {
        return ResponseEntity.ok(shopService.getUserInventory());
    }
}
