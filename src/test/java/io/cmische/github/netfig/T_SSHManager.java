package io.cmische.github.netfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class T_SSHManager {

    @Test
    public void testSendCommand() {

        try {
            JSch.setLogger(new Logger.MyLogger());
            JSch jsch = new JSch();

            String host = "cisco@10.1.10.122";
            //String host = JOptionPane.showInputDialog("Enter username@hostname", System.getProperty("user.name") + "@localhost");

            String user = host.substring(0, host.indexOf('@'));
            host = host.substring(host.indexOf('@') + 1);

            Session session = jsch.getSession(user, host, 22);

            // username and password will be given via UserInfo interface.
            UserInfo ui = new Logger.MyUserInfo();
            session.setUserInfo(ui);

            session.connect();

            Channel channel = session.openChannel("shell");

            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);

            channel.connect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
