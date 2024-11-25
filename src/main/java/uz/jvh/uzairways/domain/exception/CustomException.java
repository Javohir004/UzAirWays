package uz.jvh.uzairways.domain.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final int errorCode;
    private final HttpStatus status;

    public CustomException(String message, int errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
