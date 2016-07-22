package conf;

import io.dropwizard.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by garvit.b on 22/07/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends Configuration {
	@JsonProperty("userName")
	private String userName;

	@JsonProperty("password")
	private String password;
}
