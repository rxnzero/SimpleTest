package com.samples.socket.proxy;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by oksuz on 29/10/2017.
 */
public class Connection implements Runnable {

    private final Socket clientsocket;
    private final String remoteIp;
    private final int remotePort;
    private Socket serverConnection = null;
    
    private TcpIpProxy proxy;
    
    public Connection(Socket clientsocket, String remoteIp, int remotePort, TcpIpProxy proxy) {
        this.clientsocket = clientsocket;
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
        this.proxy = proxy;
    }

    @Override
    public void run() {
    	System.out.println( String.format("new connection %s:%s", clientsocket.getInetAddress().getHostName(), clientsocket.getPort()) );
        try {
            serverConnection = new Socket(remoteIp, remotePort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println( String.format("Proxy %s:%s <-> %s:%s", clientsocket.getInetAddress().getHostName(), clientsocket.getPort(), serverConnection.getInetAddress().getHostName(), serverConnection.getPort()) );

        new Thread(new Proxy(clientsocket, serverConnection)).start();
        new Thread(new Proxy(serverConnection, clientsocket)).start();
        new Thread(() -> {
            while (true) {
                if (clientsocket.isClosed()) {
                	proxy.removeConnection();
                	System.out.println( String.format("client socket (%s:%s) closed - %d / %d ", clientsocket.getInetAddress().getHostName(), clientsocket.getPort()
                			,proxy.getCurrentConnectionCount(), proxy.getMaxConnectionCount()) );
                	closeServerConnection();
                    break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        }).start();
        proxy.addConnection();
    }

    private void closeServerConnection() {
        if (serverConnection != null && !serverConnection.isClosed()) {
            try {
                System.out.println( String.format("closing remote host connection %s:%s", serverConnection.getInetAddress().getHostName(), serverConnection.getPort()) );
                serverConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
