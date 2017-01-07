package io.cmische.github.netfig.ssh;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SSH {

    public static void login(Credentials credentials, InputStream inputStream, OutputStream outputStream) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(credentials.username, credentials.host, 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        session.setOutputStream(outputStream);
        session.setInputStream(inputStream);
        session.setPassword(credentials.password);
        if (!credentials.validatehostkey) {
                // Setting this config might overwrite any existing session config
            session.setConfig("StrictHostKeyChecking", "no");
        }
        try {
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        Channel channel = null;
        try {
            channel = session.openChannel("shell");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        channel.setInputStream(inputStream);
        channel.setOutputStream(outputStream);
        try {
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}