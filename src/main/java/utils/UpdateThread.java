package utils;

import dto.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import views.MainView;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UpdateThread implements Runnable {
    private final static Logger log = LogManager.getLogger(UpdateThread.class);

    private LoginUser lu;

    private MainView mv;

    public UpdateThread(LoginUser lu, MainView mv) {
        this.lu = lu;
        this.mv = mv;
    }

    @Override
    public void run() {
        // Get server version
        StringBuilder serverVersion = new StringBuilder("");

        try {
            if (checkServerVersion(serverVersion)) {
                mv.showMessageDialog(Props.getLangProperty("Update.newVersion"), JOptionPane.ERROR_MESSAGE);
            } else {
                //TODO Check the diff version is higher and set it
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            log.error("Could not find server version.", e);
        }
    }

    protected boolean checkServerVersion(StringBuilder serverVersion) throws MalformedURLException {
        URL url = new URL(lu.getServerIPPort() + "/download/version.js");
        try (Scanner s = new Scanner(url.openStream(), "utf-8")) {
            while (s.hasNext()) {
                serverVersion.append(s.next());
            }
        } catch (IOException e) {
            log.error("Update Version file not found");
            return false;
        }
        String[] split = serverVersion.toString().split("\"");

        String serverVersions = split[1];

        String localVersion = Props.getGlobalProperty("Version");
        log.info(serverVersions + " " + localVersion);
        boolean b = !serverVersions.equals(localVersion);
        return b;
    }
}
