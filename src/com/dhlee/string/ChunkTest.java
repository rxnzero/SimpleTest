package com.dhlee.string;

import java.io.UnsupportedEncodingException;

public class ChunkTest {

	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();
	public static String bytesToHex(byte[] bytes) {
	    byte[] hexChars = new byte[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String sampleString = "123ÇÑùÛí­abc";
//		testChunk(sampleString, false, false);
//		testChunk(sampleString, true, false);
		
//		testChunk(sampleString, false, true);
//		testChunk(sampleString, true, true);
//		System.out.println(bytesToHex(sampleString.getBytes()) );
		
		String encoding = "ms949";
		int length = 4;
		int cLength = 0;
		cLength = getChunkSize(sampleString, encoding, length);
		
		encoding = "utf-8";
		length = 4;
		cLength = getChunkSize(sampleString, encoding, length);
		length = 5;
		cLength = getChunkSize(sampleString, encoding, length);
		length = 6;
		cLength = getChunkSize(sampleString, encoding, length);
	}
	
public static int getChunkSize(String orgString, String encoding, int length) throws UnsupportedEncodingException {
	int chunkSize = length;
	System.out.println(">> Chunk bytes length orgString[" + orgString + "], encoding=" +encoding+ ", length=" +length);
	
	byte[] orgBytes = orgString.getBytes(encoding);
	byte[] newBytes = new byte[length]; 
	System.arraycopy(orgBytes, 0, newBytes, 0, newBytes.length);
	String cutString = new String(newBytes, encoding);
	
//	for(int i=0; i<cutString.length(); i++) {
//		System.out.println(cutString.charAt(i) +" = " + Character.getType(cutString.charAt(i)) );
//	}
	
	byte[] lastCharBytes = Character.toString(cutString.charAt(cutString.length() - 1)).getBytes(encoding);
	System.out.println(">> lastCharBytes length = " + lastCharBytes.length);
	
	if( Character.getType( cutString.charAt(cutString.length() - 1) ) == 28) {
		byte[] cBytes = cutString.substring(0, cutString.length() -1).getBytes(encoding);
		
		chunkSize = cBytes.length;
	}
	else {
		chunkSize = length;
	}
	System.out.println(">> Chunk bytes length = " + chunkSize);
	return chunkSize;
}

public static void testChunk(String sampleString, boolean isUtf8, boolean isNew) {
		int chunkSize = 3;
//		
//		if(isNew)
//			testChunkNew(sampleString, chunkSize, isUtf8);
//		else 
//			testChunk(sampleString, chunkSize, isUtf8);
		
		chunkSize = 4;
		if(isNew)
			testChunkNew(sampleString, chunkSize, isUtf8);
		else 
			testChunk(sampleString, chunkSize, isUtf8);
		
		chunkSize = 5;
		if(isNew)
			testChunkNew(sampleString, chunkSize, isUtf8);
		else 
			testChunk(sampleString, chunkSize, isUtf8);
		
		chunkSize = 6;
		if(isNew)
			testChunkNew(sampleString, chunkSize, isUtf8);
		else 
			testChunk(sampleString, chunkSize, isUtf8);
		
	}
	
	public static void testChunk(String sampleString, int chunkSize, boolean isUtf8) {
		System.out.println("\nOLD chunkSize = " + chunkSize + ", isUtf8="+isUtf8 );
		String[] div = chunkString(sampleString, chunkSize, isUtf8);
		System.out.println(div[0]);
		System.out.println(div[1]);
	}

	public static void testChunkNew(String sampleString, int chunkSize, boolean isUtf8) {
		System.out.println("\nNEW chunkSize = " + chunkSize + ", isUtf8="+isUtf8 );
		String[] div = chunkStringNew(sampleString, chunkSize, isUtf8);
		System.out.println(div[0]);
		System.out.println(div[1]);
	}

	public static String[] chunkString(String sValue, int length, boolean isDbUtf8) {
    	
   	 String[] split = new String[2];
   	 
   	if(sValue == null) {
			return split;
		}
		if(length <= 0) {
			return split;
		}
		
		byte[] bytes = null;
		int sealCharSize = 2;
		if(isDbUtf8) {
			try {
				bytes = sValue.getBytes("utf-8");
				sealCharSize = 3;
			} catch (UnsupportedEncodingException e) {
//				System.out.println("utf-8 not supported - " +e.toString());
				bytes = sValue.getBytes();
				sealCharSize = 2;
			}
		}
		else {
			bytes = sValue.getBytes();
			sealCharSize = 2;
		}
		
		if(bytes.length <= length) {
			split[0] = sValue;
			split[1] = "";
			return split;
		}
		
		// if only simple characters
		if(bytes.length == sValue.length()) {
			byte[] singleChunkBytes = new byte[length];
			byte[] singleRemainBytes 	= new byte[bytes.length - length];
			
			System.arraycopy(bytes, 0, singleChunkBytes, 0, singleChunkBytes.length);
			System.arraycopy(bytes, length, singleRemainBytes, 0, singleRemainBytes.length);
			
			split[0] = new String(singleChunkBytes);
			split[1] = new String(singleRemainBytes);
			return split;
		}
		
		char[] cArray = sValue.toCharArray();
		
		int remainSize = length;
		
		for(int i = 0; i<cArray.length; i++) {
			if(cArray[i] < 256) {
				remainSize -=1;
			}
			else {
				remainSize -= sealCharSize;
			}
			
			if(remainSize <=0) {
				break;
			}
		}
		
		
		int returnSize = length;
		if(remainSize < 0) {
			returnSize = returnSize - (sealCharSize + remainSize);
		}
		
		byte[] chunkBytes = new byte[returnSize];
		byte[] remainBytes 	= new byte[bytes.length - returnSize];
		
		System.arraycopy(bytes, 0, chunkBytes, 0, chunkBytes.length);
		System.arraycopy(bytes, returnSize, remainBytes, 0, remainBytes.length);
		if(isDbUtf8) {
			try {
				split[0] = new String(chunkBytes, "utf-8");
				split[1] = new String(remainBytes, "utf-8");
			} catch (UnsupportedEncodingException e) {
				split[0] = new String(chunkBytes);
				split[1] = new String(remainBytes);
			}
		}
		else {
			split[0] = new String(chunkBytes);
			split[1] = new String(remainBytes);
		}
		
   	return split;
   }

	public static String[] chunkStringNew(String sValue, int length, boolean isDbUtf8) {
		
	  	 String[] split = new String[2];
	  	 
	  	if(sValue == null) {
				return split;
			}
			if(length <= 0) {
				return split;
			}
			
			byte[] bytes = null;
			int sealCharSize = 2;
			if(isDbUtf8) {
				try {
					bytes = sValue.getBytes("utf-8");
					sealCharSize = 3;
				} catch (UnsupportedEncodingException e) {
	//				System.out.println("utf-8 not supported - " +e.toString());
					bytes = sValue.getBytes();
					sealCharSize = 2;
				}
			}
			else {
				bytes = sValue.getBytes();
				sealCharSize = 2;
			}
			
			if(bytes.length <= length) {
				split[0] = sValue;
				split[1] = "";
				return split;
			}
			
			// if only simple characters
			if(bytes.length == sValue.length()) {
				byte[] singleChunkBytes = new byte[length];
				byte[] singleRemainBytes 	= new byte[bytes.length - length];
				
				System.arraycopy(bytes, 0, singleChunkBytes, 0, singleChunkBytes.length);
				System.arraycopy(bytes, length, singleRemainBytes, 0, singleRemainBytes.length);
				
				split[0] = new String(singleChunkBytes);
				split[1] = new String(singleRemainBytes);
				return split;
			}
			
			byte[] lastSeal = new byte[sealCharSize];
			System.arraycopy(bytes, bytes.length-sealCharSize, lastSeal, 0, lastSeal.length);
			
			int returnSize = length;
			String lastSealStr = null;
			if(isDbUtf8) {
				try {
					lastSealStr = new String(lastSeal, "utf-8");
				} catch (UnsupportedEncodingException e) {
					lastSealStr = new String(lastSeal);
				}
				char[] cArray = lastSealStr.toCharArray();
				
				
				if(cArray.length == 1) {
					returnSize = length;
				}
				else if(cArray.length == 2){
					if(cArray[0] < 256) {
						returnSize = length - 2;
					}
					else {
						returnSize = length - 1;
					}
				}
				else if(cArray.length == 3){
					if(cArray[2] >= 256) {
						returnSize = length - 1;
					}
					else {
						returnSize = length;	
					}
				} 
			}
			else {
				try {
					lastSealStr = new String(lastSeal, "ms949");
				} catch (UnsupportedEncodingException e) {
					lastSealStr = new String(lastSeal);
				}
				char[] cArray = lastSealStr.toCharArray();
				
				if(lastSeal.length == cArray.length) {
					returnSize = length;
				}
				else {
					returnSize = length-1;
				}
			}
			
			byte[] chunkBytes = new byte[returnSize];
			byte[] remainBytes 	= new byte[bytes.length - returnSize];
			
			System.arraycopy(bytes, 0, chunkBytes, 0, chunkBytes.length);
			System.arraycopy(bytes, returnSize, remainBytes, 0, remainBytes.length);
			if(isDbUtf8) {
				try {
					split[0] = new String(chunkBytes, "utf-8");
					split[1] = new String(remainBytes, "utf-8");
				} catch (UnsupportedEncodingException e) {
					split[0] = new String(chunkBytes);
					split[1] = new String(remainBytes);
				}
			}
			else {
				split[0] = new String(chunkBytes);
				split[1] = new String(remainBytes);
			}
			
	  	return split;
	  }
}