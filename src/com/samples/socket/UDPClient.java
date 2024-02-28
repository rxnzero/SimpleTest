package com.samples.socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // 서버의 IP 주소와 포트 설정
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 9876;

            // 소켓 생성
            socket = new DatagramSocket();

            // 보낼 데이터 생성
            String message = "Hello, server!";
            byte[] sendData = message.getBytes();

            // 서버로 데이터 송신
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // 서버로부터 응답 수신
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String responseMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from server: " + responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
