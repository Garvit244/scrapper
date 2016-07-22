import conf.UserInfo;
import lombok.NoArgsConstructor;

import javax.mail.*;
import java.util.Properties;

@NoArgsConstructor
public class MessagesExtracter {

	final int MAX_MESSAGES=3;
	public Message[] getMailOfUser(UserInfo userInfo) throws MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getInstance(props, null);
		Store store = session.getStore();
		store.connect("imap.gmail.com", userInfo.getUserName(), userInfo.getPassword());

		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		int end = inbox.getMessageCount();
		int start = end - MAX_MESSAGES + 1;
		Message messageReverse[] = reverseMessageOrder(inbox.getMessages(start, end));
		return messageReverse;
	}

	private static Message[] reverseMessageOrder(Message[] messages) {
		Message revMessages[]= new Message[messages.length];
		int i=messages.length-1;
		for (int j=0;j<messages.length;j++,i--) {
			revMessages[j] = messages[i];
		}
		return revMessages;
	}
}
