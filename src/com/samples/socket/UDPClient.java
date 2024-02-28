package com.samples.socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // ������ IP �ּҿ� ��Ʈ ����
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 9876;

            // ���� ����
            socket = new DatagramSocket();

            // ���� ������ ����
            String message = "Hello, server!";
            byte[] sendData = message.getBytes();

            // ������ ������ �۽�
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // �����κ��� ���� ����
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
