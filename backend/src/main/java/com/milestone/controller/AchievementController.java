package com.milestone.controller;

import com.milestone.dto.AchievementDto;
import com.milestone.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAll() {
        return ResponseEntity.ok(achievementService.getUserAchievements());
    }
}
