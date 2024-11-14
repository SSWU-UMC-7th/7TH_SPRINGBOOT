package umc.spring.apiPayLoad.code;

import umc.spring.apiPayLoad.code.ReasonDTO;

public interface BaseErrorCode {

    ErrorReasonDTO getReason();

    ErrorReasonDTO getReasonHttpStatus();
}
