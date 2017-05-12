package pl.denisolek.Exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("ClassWithoutNoArgConstructor")
public class ServiceException extends RuntimeException {

    private HttpStatus httpStatus  = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}