package pl.denisolek.Exception;

import lombok.Data;

@Data
public class ResponseTemplate {
	String code;
	String message;

	public ResponseTemplate(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
