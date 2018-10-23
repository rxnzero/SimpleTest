package com.samples.socket.proxy;

import java.util.Objects;

/**
 * Created by oksuz on 29/10/2017.
 */
public class AppMain {

    public static void main(String args[]) {

//        Objects.requireNonNull(System.getProperty("remoteHost"), "remoteHost cannot be null. use -DremoteHost=server.tld");
//        Objects.requireNonNull(System.getProperty("remotePort"), "remotePort cannot be null. use -DremotePort=5000");
//        Objects.requireNonNull(System.getProperty("port"), "port cannot be null. use -Dport=50001");

        String remoteHost = "localhost"; //System.getProperty("remoteHost");
        int remotePort = 8080; //Integer.parseInt(System.getProperty("remotePort"));
        int port = 8001; //Integer.parseInt(System.getProperty("port"));

        System.out.println(String.format("Starting proxy server on port %s for remote %s:%s", port, remoteHost, remotePort) );

        TcpIpProxy tcpIpProxy = new TcpIpProxy(remoteHost, remotePort, port, 0);
        tcpIpProxy.listen();
    }


}
