// BaseCode 인터페이스
package umc.spring.apiPayLoad.code;

import umc.spring.apiPayLoad.code.ReasonDTO;

public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();
}
