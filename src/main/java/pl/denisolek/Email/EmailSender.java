package pl.denisolek.Email;

public interface EmailSender {
	void sendEmail(String target, String subject, String content);
}
