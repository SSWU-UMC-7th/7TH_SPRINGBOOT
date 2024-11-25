package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.service.MissionService.MissionService;
import umc.spring.validation.annotation.ValidatedPage;
import umc.spring.web.dto.mission.MissionRequestDTO;
import umc.spring.web.dto.mission.MissionResponse;
import umc.spring.web.dto.mission.MissionResponseDTO;

@RestController
@Validated
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<?> addMission(@RequestBody @Valid MissionRequestDTO dto) {
        missionService.addMission(dto);
        return ResponseEntity.ok("Mission added successfully.");
    }
    @GetMapping("/store/{storeId}")
    @Operation(summary = "특정 가게의 미션 목록 조회", description = "특정 가게의 미션 목록을 페이지네이션하여 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음")
    })
    public ResponseEntity<Page<MissionResponseDTO>> getMissionsByStoreId(
            @PathVariable("storeId") Long storeId, // 경로 변수로 storeId 입력받음
            @ValidatedPage @RequestParam(name = "page", defaultValue = "1") int page, // 페이지 번호 (1 이상)
            @RequestParam(name = "size", defaultValue = "10") int size // 페이지 크기 (기본값 10)
    ) {
        Page<MissionResponseDTO> missions = missionService.getMissionsByStoreId(storeId, page, size);
        return ResponseEntity.ok(missions);
    }

    @GetMapping("/ongoing")
    @Operation(summary = "진행 중인 미션 목록 조회", description = "페이징 처리된 진행 중인 미션 목록을 반환합니다.")
    public ResponseEntity<Page<MissionResponse>> getOngoingMissions(
            @Parameter(description = "조회할 페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @Parameter(description = "한 페이지에 표시할 항목 수", example = "10")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "정렬 기준 (예: 'field,asc')", example = "createdAt,desc")
            @RequestParam(name = "sort", required = false) String sort) {
        Page<MissionResponse> missions = missionService.getOngoingMissions(
                PageRequest.of(page - 1, size, sort == null ? Sort.unsorted() : Sort.by(sort.split(",")))
        );
        return ResponseEntity.ok(missions);
    }
    @PutMapping("/{missionId}/complete")
    @Operation(summary = "미션 완료 처리", description = "진행 중인 미션의 상태를 완료(COMPLETED)로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "미션 완료 성공"),
            @ApiResponse(responseCode = "400", description = "이미 완료된 미션"),
            @ApiResponse(responseCode = "404", description = "미션을 찾을 수 없음")
    })
    public ResponseEntity<String> completeMission(
            @Parameter(description = "완료할 미션의 ID", required = true, example = "1")
            @PathVariable("missionId") Long missionId) { // `missionId`를 명시적으로 선언
        missionService.completeMission(missionId);
        return ResponseEntity.ok("미션 상태가 완료로 변경되었습니다.");
    }

}