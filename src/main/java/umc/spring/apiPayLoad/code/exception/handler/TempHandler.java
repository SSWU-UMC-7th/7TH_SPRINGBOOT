package umc.spring.apiPayLoad.code.exception.handler;

import umc.spring.apiPayLoad.code.BaseErrorCode;
import umc.spring.apiPayLoad.code.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}