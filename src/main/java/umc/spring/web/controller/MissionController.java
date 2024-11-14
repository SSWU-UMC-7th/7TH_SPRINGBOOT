package umc.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.service.MissionService.MissionService;
import umc.spring.web.dto.mission.MissionRequestDTO;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<?> addMission(@RequestBody @Valid MissionRequestDTO dto) {
        missionService.addMission(dto);
        return ResponseEntity.ok("Mission added successfully.");
    }
}