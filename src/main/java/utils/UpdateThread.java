package utils;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.LoginUser;
import views.Main;

public class UpdateThread implements Runnable {
	private final static Logger log = LogManager.getLogger(UpdateThread.class);

	private LoginUser lu;
	private Main main;

	public UpdateThread(LoginUser lu, Main main) {
		this.lu = lu;
		this.main = main;
	}

	@Override
	public void run() {
		// Get server version
		String serverVersion = "";

		try {

			URL url = new URL(lu.getServerIPPort() + "/download/version.js");
			try (Scanner s = new Scanner(url.openStream())) {
				while (s.hasNext()) {
					serverVersion += s.next();
				}
			}
			String[] split = serverVersion.split("\"");

			serverVersion = split[1];

			String localVersion = Props.getGlobalProperty("Version");
			if (!serverVersion.equals(localVersion)) {
				main.showMessageDialog(Props.getLangProperty("Update.newVersion"), JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			log.error("Could not find server version.", e);
		}
	}
}
