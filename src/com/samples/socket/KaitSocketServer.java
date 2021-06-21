package com.samples.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class KaitSocketServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		int port = 20001;
		try {
			// 서버 생성
			serverSocket = new ServerSocket(port);
			System.out.println("START port="+ port);
			// client 접속 accept
			socket = serverSocket.accept();
			// client가 보낸 데이터 출력
			in = socket.getInputStream();
			out = socket.getOutputStream();
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(in));
			String message = bufReader.readLine();
			System.out.println("Message : " + message);
			System.out.println(DumpUtil.getDumpMessage(message.getBytes()));
			// client에 데이터 전송
			BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(out));
			bufWriter.write("0000000050KAIT000001G  20210127ABCD0000005       \n");
			bufWriter.write("123456789\n");
			bufWriter.flush();
			Thread.sleep(1 * 1000);
			bufWriter.write("123456789\n");
			bufWriter.flush();
			
			Thread.sleep(1 * 1000);
			bufWriter.write("123456789\n");
			bufWriter.flush();
			
			Thread.sleep(1 * 1000);
			bufWriter.write("123456789\n");
			bufWriter.flush();
			
			Thread.sleep(1 * 1000);
			bufWriter.write("123456789\n");
			bufWriter.flush();
			socket.close();
			serverSocket.close();
			bufReader.close();
			bufWriter.close();
			Thread.sleep(10 * 1000);
			System.out.println("END...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(in != null)
				try { in.close(); } catch (IOException e) { e.printStackTrace(); }
			if(out != null) 
				try { out.close(); } catch (IOException e) { e.printStackTrace(); }
			if(socket != null) 
				try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
			if(serverSocket != null) 
				try { serverSocket.close(); } catch (IOException e) { e.printStackTrace(); }
		}
	}
}