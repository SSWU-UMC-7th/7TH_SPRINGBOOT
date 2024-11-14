package umc.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.domain.Store;
import umc.spring.service.StoreService.StoreCommandService;
import umc.spring.web.dto.store.StoreRequestDTO;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreCommandService storeCommandService;

    @PostMapping
    public ResponseEntity<?> addStore(@RequestBody @Valid StoreRequestDTO dto) {
        Store store = storeCommandService.addStore(dto);
        return ResponseEntity.ok("Store added successfully with ID: " + store.getId());
    }
}
