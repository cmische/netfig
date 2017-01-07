package io.cmische.github.netfig.ssh;

public class Credentials {
    String host;
    String username;
    String password;
    boolean validatehostkey = true;

    public Credentials(String host, String username, String password){
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public Credentials(String host, String username, String password, Boolean validatehostkey){
        this.host = host;
        this.username = username;
        this.password = password;
        this.validatehostkey = validatehostkey;
    }
}
