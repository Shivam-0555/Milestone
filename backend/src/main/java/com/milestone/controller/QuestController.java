package com.milestone.controller;

import com.milestone.dto.QuestRequest;
import com.milestone.dto.QuestResponse;
import com.milestone.model.Quest;
import com.milestone.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quests")
public class QuestController {

    @Autowired
    private QuestService questService;

    @GetMapping("/heatmap")
    public ResponseEntity<List<com.milestone.dto.HeatmapEntry>> getHeatmap() {
        return ResponseEntity.ok(questService.getHeatmap());
    }

    @GetMapping
    public ResponseEntity<List<QuestResponse>> getAll() {
        List<Quest> quests = questService.getUserQuests();
        List<QuestResponse> resp = quests.stream()
                .map(QuestResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<QuestResponse> create(@Valid @RequestBody QuestRequest request) {
        Quest quest = questService.createQuest(request);
        return ResponseEntity.ok(QuestResponse.fromEntity(quest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestResponse> get(@PathVariable Long id) {
        Quest quest = questService.getQuest(id);
        return ResponseEntity.ok(QuestResponse.fromEntity(quest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestResponse> update(@PathVariable Long id, @Valid @RequestBody QuestRequest request) {
        Quest quest = questService.updateQuest(id, request);
        return ResponseEntity.ok(QuestResponse.fromEntity(quest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questService.deleteQuest(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<com.milestone.dto.QuestCompletionResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(questService.completeQuest(id));
    }
}
