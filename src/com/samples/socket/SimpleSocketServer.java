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
		BufferedReader bufReader = null;
		BufferedWriter bufWriter = null;
		boolean sendResponse = false;
		try {
			// 서버 생성
			serverSocket = new ServerSocket(43001);
			// client 접속 accept
			socket = serverSocket.accept();
			// client가 보낸 데이터 출력
			in = socket.getInputStream();
			bufReader = new BufferedReader(new InputStreamReader(in));
			out = socket.getOutputStream();
			bufWriter = new BufferedWriter(new OutputStreamWriter(out));
			while(true) {
				String message = bufReader.readLine();
				System.out.println("Message : " + message);
				// client에 데이터 전송
				if(sendResponse) {
					bufWriter.write("hello world");
					bufWriter.newLine();
					bufWriter.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufReader != null)
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bufWriter != null)
				try {
					bufWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}