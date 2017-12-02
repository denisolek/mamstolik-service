package pl.denisolek.Exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("ClassWithoutNoArgConstructor")
public class ServiceException extends RuntimeException {

	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	private ResponseTemplate body;

	public ServiceException(HttpStatus httpStatus, ResponseTemplate body) {
		super(body.message);
		this.body = body;
		this.httpStatus = httpStatus;
	}

	public ServiceException(HttpStatus httpStatus, String body) {
		super(body);
		this.body = new ResponseTemplate(httpStatus.toString(), body);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ResponseTemplate getBody() {
		return body;
	}
}