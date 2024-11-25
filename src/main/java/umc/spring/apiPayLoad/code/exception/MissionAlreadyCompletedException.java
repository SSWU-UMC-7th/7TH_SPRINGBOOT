package umc.spring.apiPayLoad.code.exception;

public class MissionAlreadyCompletedException extends RuntimeException {
    public MissionAlreadyCompletedException(String message) {
        super(message);
    }
}
