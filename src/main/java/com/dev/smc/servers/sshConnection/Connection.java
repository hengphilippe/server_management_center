package com.dev.smc.servers.sshConnection;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Connection {
    public static Boolean verifyConnectionByPassword(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);
            // Avoid asking for key confirmation
            session.setConfig("StrictHostKeyChecking", "no");
            // Connect to the server
            session.connect();
            System.out.println("Connected to " + host);
            session.disconnect();
            return true;
        } catch (JSchException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
        return false;
    }

    public Boolean verifyConnectionByKey(String host, int port, String username, String keyPath) {
        return false;
    }
    public static void main(String[] args) {
        String remotePath = "/tmp";
        String fileName = "text.txt";

        String user = "oracle";
        String password = "oracle";
        String host = "10.0.10.4";
        int port = 22;
        String cmd = "whoami";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // Avoid asking for key confirmation
            session.setConfig("StrictHostKeyChecking", "no");
            // Connect to the server
            session.connect();
            System.out.println("Connected to " + host + ":" + port);
            // Create a channel to execute the command
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(cmd);
            System.out.println("Executing command: " + cmd);
            // Get the input stream for the command output
            InputStream in = channel.getInputStream();
            channel.connect();

            // Read the output from the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Clean up
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}