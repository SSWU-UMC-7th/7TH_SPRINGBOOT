package umc.spring.converter;

import org.springframework.stereotype.Component;
import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.web.dto.store.StoreRequestDTO;

@Component
public class StoreConverter {
    public static Store toStore(StoreRequestDTO dto, Region region) {
        return Store.builder()
                .name(dto.getName())
                .region(region)
                .build();
    }
}