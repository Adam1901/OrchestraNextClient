package dto;

public class LoginUser {
    private String username;
    private String password;
    private String serverIPPort;
    private boolean additionalUser = false;

    public boolean isAdditionalUser() {
        return additionalUser;
    }

    public void setAdditionalUser(boolean additionalUser) {
        this.additionalUser = additionalUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerIPPort() {
        return serverIPPort;
    }

    public void setServerIPPort(String serverIPPort) {
        this.serverIPPort = serverIPPort;
    }

    public LoginUser(String username, String password, String serverIPPort) {
        super();
        this.username = username;
        this.password = password;
        this.serverIPPort = serverIPPort;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", serverIPPort='" + serverIPPort + '\'' +
                ", additionalUser=" + additionalUser +
                '}';
    }
}
