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
		String ip = "230.0.0.1"; // "224.128.1.5";
		int port = 4443; // 9006;
		InetAddress address = null;
		
		if(args.length  == 1) {
			ip = args[0];
		}
		
		if(args.length  == 2) {
			ip = args[0];
			try {
				port = Integer.parseInt(args[1]);
			}
			catch(Exception e) {
				;
			}
		}
		
		try {
			socket = new MulticastSocket(port);
			System.out.println("Multicast Receiver init . ip=" + ip + ", port=" + port);

			// 그룹에 조인(라우터가 보냄)
			address = InetAddress.getByName(ip); // 멀티 캐스트를 위한 아이피 설정
			socket.joinGroup(address);

			byte[] buf = new byte[512];

			packet = new DatagramPacket(buf, buf.length);

			while (true) {
				// 패킷 수신
				socket.receive(packet);

				String msg = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Receive > " + msg);
				if ("quit".equalsIgnoreCase(msg)) {
					System.out.println("# quit test.");
					break;
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && address != null) {
				try {
					socket.leaveGroup(address);
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
