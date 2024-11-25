package umc.spring.apiPayLoad.code.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import umc.spring.apiPayLoad.ApiResponse;
import umc.spring.apiPayLoad.code.ErrorReasonDTO;
import umc.spring.apiPayLoad.code.status.ErrorStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    // Validation 실패 시 예외 처리 메서드
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Object> body = ApiResponse.onFailure(String.valueOf(HttpStatus.BAD_REQUEST.value()), "요청 값 검증 실패", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // HttpMessageNotReadableException 오버라이드
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = "요청의 JSON 형식이 잘못되었습니다. 요청 데이터를 확인하세요.";

        // 예외 메시지를 로그로 기록
        log.error("HttpMessageNotReadableException 발생: {}", ex.getMessage());

        ApiResponse<Object> body = ApiResponse.onFailure(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorMessage, null);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElse("ConstraintViolationException 발생");

        return handleExceptionInternalConstraint(e, HttpStatus.BAD_REQUEST, HttpHeaders.EMPTY, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        log.error("Exception 발생: ", e);
        return handleExceptionInternalFalse(e, HttpStatus.INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request, e.getMessage());
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException, errorReasonHttpStatus, null, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(e, body, headers, reason.getHttpStatus(), webRequest);
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, HttpStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(String.valueOf(errorCommonStatus.value()), errorCommonStatus.getReasonPhrase(), errorPoint);
        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, HttpStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(String.valueOf(errorCommonStatus.value()), errorCommonStatus.getReasonPhrase(), errorArgs);
        return super.handleExceptionInternal(e, body, headers, errorCommonStatus, request);
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, HttpStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(String.valueOf(errorCommonStatus.value()), errorCommonStatus.getReasonPhrase(), null);
        return super.handleExceptionInternal(e, body, headers, errorCommonStatus, request);
    }

    @ExceptionHandler(MissionNotFoundException.class)
    public ResponseEntity<String> handleMissionNotFoundException(MissionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MissionAlreadyCompletedException.class)
    public ResponseEntity<String> handleMissionAlreadyCompletedException(MissionAlreadyCompletedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidMissionStatusException.class)
    public ResponseEntity<String> handleInvalidMissionStatusException(InvalidMissionStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
