import conf.UserInfo;

import javax.mail.Message;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/get")
@Produces(MediaType.APPLICATION_JSON)
public class ScrapperResource {
	private final String userId;
	private final String passwd;

	public ScrapperResource(String userId, String password) {
		this.userId = userId;
		this.passwd = password;
	}

	@GET
	public Map<String, String> sayHello() {
		MessagesExtracter messagesExtracter = new MessagesExtracter();
		Map<String, String> userLeaveInfos = new HashMap<String, String>();
		UserInfo userInfo = new UserInfo(userId, passwd);
		try {
			Message[] messages = messagesExtracter.getMailOfUser(userInfo);
			ExtractInfos extractInfos = new ExtractInfos();
			userLeaveInfos = extractInfos.extract(messages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLeaveInfos;

	}
}