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

public class SimpleSocketServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			// 서버 생성
			serverSocket = new ServerSocket(20001);
			// client 접속 accept
			socket = serverSocket.accept();
			// client가 보낸 데이터 출력
			in = socket.getInputStream();
			out = socket.getOutputStream();
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(in));
			String message = bufReader.readLine();
			System.out.println("Message : " + message);
			// client에 데이터 전송
			BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(out));
			bufWriter.write("hello world");
			bufWriter.newLine();
			bufWriter.flush();
			socket.close();
			serverSocket.close();
			bufReader.close();
			bufWriter.close();
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