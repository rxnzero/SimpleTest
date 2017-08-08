package com.samples.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {    
	private String getLength(byte[] message) {
		String length = "0000";
		length = String.format("%04d", message.length);
		return length;
	}
	
	private byte[] getLengthByte(byte[] message) {
		return getLength(message).getBytes();
	}
	
	public int toLength(byte[] length) {
		return Integer.parseInt( (new String(length)).trim() );
	}
	
    public void sendToServer(String ip, int port,  String message) {

    	Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        
        byte[] lengthBytes = new byte[4];
        try {
	        socket = new Socket(ip,port);
	        is = socket.getInputStream();
	        os = socket.getOutputStream();
	        
	        
	        // 한글일 경우에는 encoding 처리가 필요함
	        // line.getBytes("utf-8")
	        byte[] msgBytes = message.getBytes();
	        
	        byte[] sendLength = getLengthByte(msgBytes);
	        
	        os.write(sendLength);
	        
	        os.write(msgBytes);
	        os.flush();
	        System.out.println("request - length header : " + new String(sendLength) + "\n" + message);
	        
	        int read = is.read(lengthBytes);
	        
	        if(read == 4) {
	        	int responseSize = toLength(lengthBytes);
	        	
	        	byte[] response = new byte[responseSize];
	        	
	        	int readBody = is.read(response);
	        	System.out.println("response - length : " + readBody + "\n" + new String(response));	        	
	        }
	        else {
	        	System.out.println("read length field failed - " + read);
	        }	        
        }
        catch(Exception ex) {	
        	ex.printStackTrace();
        }
        finally {
        	try {
				is.close();
			} catch (IOException e) {
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

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
    	SocketClient client = new SocketClient();
    	client.sendToServer("localhost", 8888, "hello touppper socket server !");
    }
}
