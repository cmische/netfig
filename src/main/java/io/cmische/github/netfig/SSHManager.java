package io.cmische.github.netfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ChannelExec;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SSHManager  {
    private static final Logger LOGGER = Logger.getLogger(SSHManager.class.getName());
    private JSch jschSSHChannel;
    private String strUserName;
    private String strConnectionIP;
    private int intConnectionPort;
    private String strPassword;
    private Session sesConnection;
    private int intTimeOut;

    public SSHManager(String userName, String password, String connectionIP,
                      String knownHostsFileName) {
        doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
        intConnectionPort = 22;
        intTimeOut = 60000;
    }

    public SSHManager(String userName, String password, String connectionIP,
                      String knownHostsFileName, int connectionPort) {
        doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
        intConnectionPort = connectionPort;
        intTimeOut = 60000;
    }

    public SSHManager(String userName, String password, String connectionIP,
                      String knownHostsFileName, int connectionPort, int timeOutMilliseconds) {
        doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
        intConnectionPort = connectionPort;
        intTimeOut = timeOutMilliseconds;
    }

    private void doCommonConstructorActions(String userName, String password, String connectionIP, String knownHostsFileName) {
        jschSSHChannel = new JSch();

        try {
            jschSSHChannel.setKnownHosts(knownHostsFileName);
        } catch (JSchException jschX) {
            logError(jschX.getMessage());
        }
        strPassword = password;
        strConnectionIP = connectionIP;
    }

    public String connect() {
        String errorMessage = null;

        try {
            sesConnection = jschSSHChannel.getSession(strUserName,
                    strConnectionIP, intConnectionPort);
            sesConnection.setPassword(strPassword);
            Properties config = new Properties();
            // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
            config.put("StrictHostKeyChecking", "no");
            config.put("kex", "diffie-hellman-group1-sha1");
            sesConnection.setConfig(config);
            sesConnection.connect(intTimeOut);
        } catch (JSchException jschX) {
            errorMessage = jschX.getMessage();
        }

        return errorMessage;
    }

    private String logError(String errorMessage) {
        if (errorMessage != null) {
            LOGGER.log(Level.SEVERE, "{0}:{1} - {2}",
                    new Object[]{strConnectionIP, intConnectionPort, errorMessage});
        }

        return errorMessage;
    }

    private String logWarning(String warnMessage) {
        if (warnMessage != null) {
            LOGGER.log(Level.WARNING, "{0}:{1} - {2}",
                    new Object[]{strConnectionIP, intConnectionPort, warnMessage});
        }

        return warnMessage;
    }

    public String sendCommand(String command) {
        StringBuilder outputBuffer = new StringBuilder();

        try {
            Channel channel = sesConnection.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();

            while (readByte != 0xffffffff) {
                outputBuffer.append((char) readByte);
                readByte = commandOutput.read();
            }

            channel.disconnect();
        } catch (IOException ioX) {
            logWarning(ioX.getMessage());
            return null;
        } catch (JSchException jschX) {
            logWarning(jschX.getMessage());
            return null;
        }

        return outputBuffer.toString();
    }

    public void close() {
        sesConnection.disconnect();
    }

}