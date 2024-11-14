package umc.spring.service.TempService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import umc.spring.apiPayLoad.code.exception.handler.TempHandler;
import umc.spring.apiPayLoad.code.status.ErrorStatus;

@Service
@Primary
@RequiredArgsConstructor
public class TempQueryServiceImpl implements TempQueryService{

    @Override
    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
    }
}