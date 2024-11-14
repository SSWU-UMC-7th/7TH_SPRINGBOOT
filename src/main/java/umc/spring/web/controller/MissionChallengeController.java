package umc.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.service.MissionService.MissionChallengeService;
import umc.spring.web.dto.mission.MissionChallengeRequestDTO;

@RestController
@RequestMapping("/api/missions/challenge")
@RequiredArgsConstructor
public class MissionChallengeController {

    private final MissionChallengeService missionChallengeService;

    @PostMapping
    public ResponseEntity<?> challengeMission(@RequestBody @Valid MissionChallengeRequestDTO dto) {
        missionChallengeService.challengeMission(dto);
        return ResponseEntity.ok("Mission challenged successfully.");
    }
}
