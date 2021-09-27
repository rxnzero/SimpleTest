package com.samples.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MulticastClient {

	public MulticastClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		DatagramPacket packet = null;
		MulticastSocket socket = null;
		String ip = "224.128.1.5";
		int port = 9006;
		try {
			socket = new MulticastSocket(port);
			System.out.println("Ŭ���̾�Ʈ ����. ip="+ ip +", port="+port);

			// �׷쿡 ����(����Ͱ� ����)
			InetAddress address = InetAddress.getByName(ip); // ��Ƽ ĳ��Ʈ�� ���� ������ ����
			socket.joinGroup(address);

			byte[] buf = new byte[512];

			packet = new DatagramPacket(buf, buf.length);

			while (true) {
				// ��Ŷ ����
				socket.receive(packet);

				String msg = new String(packet.getData(), 0, packet.getLength());
				System.out.println("���� > " + msg);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
