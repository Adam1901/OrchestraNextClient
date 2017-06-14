package views;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrayIconHandeler {
	
	private boolean supported;

	public TrayIconHandeler() {
		supported = SystemTray.isSupported();
		
	}

	public void displayTray(String title, String message, MessageType messageType) throws AWTException, IOException {
		if (!supported) {
			System.err.println("System tray not supported!");
			return;
		}
		SystemTray tray = SystemTray.getSystemTray();

		// If the icon is a file
		Image image = ImageIO.read(getClass().getClassLoader().getResource("qmaticBigTransparent.png"));
		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		trayIcon.setImageAutoSize(true);
		tray.add(trayIcon);
		trayIcon.displayMessage(title, message, messageType);
	}
}