package com.samples.socket.proxy;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by oksuz on 29/10/2017.
 */
public class TcpIpProxy {
    private final String remoteIp;
    private final int remotePort;
    private final int port;
    private final int maxConnections;
    private int currConnections = 0;
    
    public TcpIpProxy(String remoteIp, int remotePort, int port, int maxConnections) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
        this.port = port;
        this.maxConnections = maxConnections;
    }
    
    public void listen() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("listening...");
            while (true) {
                Socket socket = serverSocket.accept();
                if(maxConnections> 0 && currConnections > maxConnections) {
                	System.out.println(String.format("MAX CONNECTION LIMIT : %d / %d - %s", currConnections, maxConnections, socket.getRemoteSocketAddress().toString()) );
                	socket.close();
                }
                else {
                	startThread(new Connection(socket, remoteIp, remotePort, this));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startThread(Connection connection) {
        Thread t = new Thread(connection);
        t.start();
    }
    
    public synchronized void addConnection() {
    	currConnections++;
    }
    
    public synchronized void removeConnection() {
    	currConnections--;
    }
    
    public int getCurrentConnectionCount() {
    	return currConnections; 
    }
    
    public int getMaxConnectionCount() {
    	return maxConnections; 
    }
}
