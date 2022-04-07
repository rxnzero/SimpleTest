package com.samples.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	
	private static String getLength(int bodySize) {
		String length = "0000";
		length = String.format("%04d", bodySize);
		return length;
	}
	
	private static byte[] getLengthBytes(int bodySize) {
		return getLength(bodySize).getBytes();
	}
	
	private static String getLength(byte[] message) {
		return getLength(message.length);
	}

	private static byte[] getLengthByte(byte[] message) {
		return getLength(message).getBytes();
	}

	public static int toLength(byte[] header) {
		int length = 0;
		try {
			length = Integer.parseInt((new String(header)).trim());
		}
		catch(Exception e) {
			length = 0;
		}
		return length;
	}
	
	private static byte[] getBinLengthByte(int lengthSize, int length) {
		int tmpLength = length;
		byte[] header = new byte[lengthSize];
		for (int i = lengthSize-1; i>=0; i--) {
			header[i] = (byte) (tmpLength % 256);
			tmpLength = tmpLength / 256;
		}
		return header;
	}
	
	public static int toBInLength(byte[] header) {
		int payloadSize = 0;
		for (int i = 0; i < header.length; i++) {
			payloadSize += ((int)header[i] & 0xFF) * Math.pow(256, header.length - i - 1);
		}
		return payloadSize;
	}
	public static void printBytes(byte[] header) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < header.length; i++) {
			sb.append(String.format("%02x", header[i]&0xff));
		}
		System.out.println(sb.toString());
	}
	
	public static int getLength(byte[] header, String type) {
		if("BIN".equals(type)) {
			return toBInLength(header);
		}
		else {
			return toLength(header);
		}
	}
	
	public static byte[] getLengthHeader(byte[] body, String type) {
		int length = body.length;
		if("BIN".equals(type)) {
			return getBinLengthByte(4, length);
		}
		else {
			return getLengthBytes(length);
		}
	}

	public static void testLength() {
		byte[]  header = getBinLengthByte(4, 0);
		printBytes(header);
		System.out.println("bin length : " + toBInLength(header));
		System.out.println("char length : " + toLength(header));
	}
	
	public static void main(String[] args) throws Exception {
		int port = 43001; // 8888
		int clientNumber = 0;
		ServerSocket listener = new ServerSocket(port);
		try {
			System.out.println("The toUpper server is running. port=" + port);
			while (true) {
				new WorkThread(listener.accept(), clientNumber++).start();
			}
		} finally {
			listener.close();
		}
	}

	private static class WorkThread extends Thread {
		private Socket socket;
		private int clientNumber;

		public WorkThread(Socket socket, int clientNumber) {
			this.socket = socket;
			this.clientNumber = clientNumber;
			System.out.println("New connection with client# " + clientNumber + " at " + socket);
		}

		public void run() {
			InputStream is = null;
			OutputStream os = null;
			byte[] lengthBytes = new byte[4];

			try {
				is = socket.getInputStream();
				os = socket.getOutputStream();
				while(true) {
					int read = is.read(lengthBytes);
					if(read == -1) {
						System.out.print("CLOSED read length : " + read);
						break;
					}
					else if (read == 4) {
						System.out.print("HEADER BYTES : ");
						printBytes(lengthBytes);
						int requestSize = getLength(lengthBytes, "BIN");
						if(requestSize <= 0) {
							System.out.println("read header length : "+ requestSize);
							continue;
						}
						byte[] request = new byte[requestSize];
	
						int readBody = is.read(request);
						System.out.println("request - length : " + readBody + "\n" + new String(request));
	
	//        	        	Thread.sleep(100);
						// line.getBytes("utf-8")
						byte[] msgBytes = (new String(request)).toUpperCase().getBytes();
	
						byte[] ll = getLengthHeader(msgBytes, "BIN");
						System.out.println("response - length : " + msgBytes.length + "\n" + new String(msgBytes));
						os.write(ll);
						os.write(msgBytes);
						os.flush();
					} else {
						System.out.println("read length field failed - " + read);
					}
//					Thread.sleep(1 * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
