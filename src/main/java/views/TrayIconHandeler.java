package views;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.Props;
import utils.Props.GlobalProperties;

public class TrayIconHandeler {
	private final static Logger log = LogManager.getLogger(TrayIconHandeler.class);
	private boolean supported;
	TrayIcon trayIcon;

	public TrayIconHandeler() {
		supported = SystemTray.isSupported();
		if (supported) {
			try {
				SystemTray tray = SystemTray.getSystemTray();
				Image image = ImageIO.read(getClass().getClassLoader().getResource("qmaticBigTransparent.png"));

				trayIcon = new TrayIcon(image, Props.getLangProperty("noti.title"));
				trayIcon.setImageAutoSize(true);
				tray.add(trayIcon);
			} catch (IOException | AWTException e) {
				log.error(e);
			}
		}
	}

	public void displayTray(String title, String message, MessageType messageType) {
		if (!supported) {
			log.error("System tray not supported!");
			return;
		}
		if (!Boolean.valueOf(Props.getGlobalProperty(GlobalProperties.NOTIFICATIONS))) {
			return;
		}
		trayIcon.displayMessage(title, message, messageType);
	}
}