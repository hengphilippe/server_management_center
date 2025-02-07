package com.dev.smc.servers.ScpFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class ScpFileTransfer {
    private static final int PORT = 22;
    private static final String USERNAME = "paongsokchamroeun";
    private static final String PASSWORD = "092954353";

    public static void uploadFile(String localFilePath, String remoteHost, String remoteDirectory) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            System.out.println("Connecting to " + remoteHost + "...");
            JSch jsch = new JSch();
            session = jsch.getSession(USERNAME, remoteHost, PORT);
            session.setPassword(PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.put(localFilePath, remoteDirectory);
            System.out.println("File uploaded successfully to " + remoteDirectory);

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        String localFilePath = "C:\\Users\\Sethz Bahh\\Downloads\\Telegram Desktop\\text12345.txt";
        String remoteHost = "192.168.47.168";
        String remoteDirectory = "/home/paongsokchamroeun/";
        uploadFile(localFilePath, remoteHost, remoteDirectory);
    }
}
