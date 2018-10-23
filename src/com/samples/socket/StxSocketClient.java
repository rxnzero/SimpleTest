package com.samples.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class StxSocketClient {    
	private byte STX = (byte)0x02;
	private byte ETX = (byte)0x03;
	
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
	        
	        os.write(STX);
	        os.write(sendLength);
	        
	        os.write(msgBytes);
	        os.write(ETX);
	        os.flush();
	        System.out.println("request - length header : " + new String(sendLength) + "\n" + message);
	        
	        Thread.sleep(5 * 1000);
	        
	        byte stx = (byte)is.read();
	        int read = is.read(lengthBytes);
	        
	        if(read == 4) {
	        	int responseSize = toLength(lengthBytes);
	        	
	        	byte[] response = new byte[responseSize];
	        	
	        	int readBody = is.read(response);
	        	
	        	byte etx = (byte)is.read();
	        	System.out.println("response - length : " + readBody + "\n" + new String(response));
	        	Thread.sleep(300 * 1000);
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
    	StxSocketClient client = new StxSocketClient();
//    	STX ETX LL(4) 는 출력하지 않음
    	client.sendToServer("localhost", 52302, "00000000000000001002000010000000001000102018061800293300007100443"); // 
    	
//    	byte[] sourceBytes = "006500000000000000001002000010000000001000102018061800293300007100443".getBytes();
//    	byte[] errorCode = "900".getBytes();
//    	System.out.println(sourceBytes.length); 
//   	 	if(sourceBytes.length > 62) {
//        	System.arraycopy(errorCode, 0, sourceBytes, 59, 3);
//        }	
//   	 System.out.println(new String( sourceBytes) ); 
    }
}
