package com.samples.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	private static String getLength(byte[] message) {
		String length = "0000";
		length = String.format("%04d", message.length);
		return length;
	}
	
	private static byte[] getLengthByte(byte[] message) {
		return getLength(message).getBytes();
	} 
	
	public static int toLength(byte[] length) {
		return Integer.parseInt( (new String(length)).trim() );
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8888;
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
        try {
        	System.out.println("The capitalization server is running. port="+ port);
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
        	        
         	       int read = is.read(lengthBytes);
        	        
        	        if(read == 4) {
        	        	int requestSize = toLength(lengthBytes);
        	        	
        	        	byte[] request = new byte[requestSize];
        	        	
        	        	int readBody = is.read(request);
        	        	System.out.println("request - length : " + readBody + "\n" + new String(request));	        	
        	        	
             	        // line.getBytes("utf-8")
             	        byte[] msgBytes = (new String(request)).toUpperCase().getBytes();
             	        os.write(getLengthByte(msgBytes));
             	        os.write(msgBytes);
             	        os.flush();
        	        }
        	        else {
        	        	System.out.println("read length field failed - " + read);
        	        }
         	        
         	        
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
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
	    }

}
