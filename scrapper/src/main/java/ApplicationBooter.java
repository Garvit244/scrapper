import conf.UserInfo;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ApplicationBooter extends Application<UserInfo> {

	@Override
	public void run(UserInfo userInfo, Environment environment) throws Exception {
		final ScrapperResource scrapperResource = new ScrapperResource(userInfo.getUserName(), userInfo.getPassword());
		environment.jersey().register(scrapperResource);
	}

	public static void main(String[] args) throws Exception {
		new ApplicationBooter().run(args);
	}

	@Override
	public String getName() {
		return "hello-world";
	}

	@Override
	public void initialize(Bootstrap<UserInfo> bootstrap) {
		// nothing to do yet
	}

}
