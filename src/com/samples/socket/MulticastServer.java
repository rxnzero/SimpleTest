package com.samples.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {

	public MulticastServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		DatagramPacket packet = null;
		MulticastSocket socket = null;
		String ip = "224.128.1.5";
		int port = 9006;
		try {
			socket = new MulticastSocket();
			System.out.println("서버 생성 성공. ip=" + ip + ", port=" + port);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			// IPv4 아래의 내용은 이전의 포스팅을 통해 설명 해두었음.
			// A Class : 0000 (0000) => 0.0.0.0 ~ 127.255.255.255
			// B Class : 1000 (0000) => 128.0.0.0 ~ 191.255.255.255
			// C Class : 1100 (0000) => 192.0.0.0 ~ 223.255.255.255
			// D Class : 1110 (0000) => 224.0.0.0 ~ 239.255.255.255
			// E Class : 1111 (0000) => 240.0.0.0 ~ 255.255.255.255
			InetAddress address = InetAddress.getByName(ip); // 멀티캐스트 방식으로 서버 주소를 설정함.

			while (true) {
				System.out.print("입력 : ");
				String msg = reader.readLine();
				if (msg == null) {
					break;
				}
				packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, port);
				socket.send(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
