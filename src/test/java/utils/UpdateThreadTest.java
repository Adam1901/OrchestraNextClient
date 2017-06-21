package utils;

import static org.junit.Assert.assertTrue;
import static utils.Props.getUserProperty;

import org.junit.Before;
import org.junit.Test;

import dto.LoginUser;
import utils.Props.GlobalProperties;

public class UpdateThreadTest {
	UpdateThread uut;
	LoginUser lu;

	@Before
	public void beforeTest() throws Exception {
		String ip = getUserProperty("ip");
		String port = getUserProperty("port");
		String proto = getUserProperty("proto");
		String username = getUserProperty("username");
		String password = Utils.decode(Props.getUserProperty("password"));

		String connectionString = proto + ip + ":" + port;

		lu = new LoginUser(username, password, connectionString);
		uut = new UpdateThread(lu, null);
	}

	@Test
	public void testCheckServerVersion() throws Exception {
		boolean checkServerVersion = uut.checkServerVersion(Props.getGlobalProperty(GlobalProperties.VERSION));
		assertTrue(checkServerVersion);
	}

}
