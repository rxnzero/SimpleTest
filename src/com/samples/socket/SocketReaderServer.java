package com.samples.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketReaderServer {
	
	
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
		int port = 21004;
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
        try {
        	System.out.println("The capitalization server is running. port="+ port);
            while (true) {
                new WorkThread(listener.accept(), clientNumber++, true).start();
            }
        } finally {
            listener.close();
        }
    }
	
	 private static class WorkThread extends Thread {
	        private Socket socket;
	        private int clientNumber;
	        boolean isPermanent = false;
	        int header_size = 4;
	        
	        public WorkThread(Socket socket, int clientNumber, boolean permanent) {
	            this.socket = socket;
	            this.clientNumber = clientNumber;
	            this.isPermanent = permanent;
	            System.out.println("New connection with client# " + clientNumber + " at " + socket);
	        }

	        public void run() {
	        	InputStream is = null;
                OutputStream os = null;
                System.out.println(">> start run...");
                byte[] lengthBytes = new byte[header_size];
                try {
        	        is = socket.getInputStream();
        	        os = socket.getOutputStream();
        	          
        	        while(isPermanent) {
	         	       int read = is.read(lengthBytes);
	         	      
	        	        if(read == header_size) {
	        	        	int requestSize = toLength(lengthBytes);
	        	        	System.out.println(">> Length Size : " + requestSize );
	  	                  
	        	        	byte[] response = new byte[requestSize];
	        	        	
	        	        	int readBody = is.read(response);
	        	        	
	        	        	System.out.println(DumpUtil.getDumpMessage(response));	        	        		        	
	        	        	
	             	        // line.getBytes("utf-8")
	//             	        byte[] msgBytes = (new String(request)).toUpperCase().getBytes();
	//             	        os.write(getLengthByte(msgBytes));
	//             	        os.write(msgBytes);
	//             	        os.flush();
	        	        }
	        	        else {
	        	        	System.out.println("read length field failed - " + read);
	        	        	break;
	        	        }
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
