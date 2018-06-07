package com.dhlee.aes;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESSample {

	public AESSample() {
		// TODO Auto-generated constructor stub
	}
	
	 public static String encrypt(String strEncryptKey , String strData){
         String strReturn = "";

         try{
             strEncryptKey = strEncryptKey.substring(0, 16);

             byte[] bKey = strEncryptKey.getBytes();

             SecretKeySpec keySpec = new SecretKeySpec(bKey, "AES");

             AlgorithmParameterSpec ivSpec = new IvParameterSpec(bKey);

             Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

             byte[] baEncryptData = null;

             try {
                 cipher.init(Cipher.ENCRYPT_MODE , keySpec ,ivSpec);
                 baEncryptData = cipher.doFinal(strData.getBytes());
             }catch (Exception e){
                 e.printStackTrace();
             }

             strReturn = new BASE64Encoder().encode(baEncryptData);

         }catch(Exception e){
             System.out.println(e);
             strReturn = strData;
         }

         return strReturn;

     }

     public static String decypt(String strDecryptKey , String strData){
         String strReturn = "";

         try{      
             strDecryptKey = strDecryptKey.substring(0, 16);

             byte[] bKey = strDecryptKey.getBytes();

             SecretKeySpec keySpec = new SecretKeySpec(bKey, "AES");

             AlgorithmParameterSpec ivSpec = new IvParameterSpec(bKey);

             Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

             byte[] baDecryptData = null;

             try {
                 cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
                 baDecryptData = cipher.doFinal(new BASE64Decoder().decodeBuffer(strData));
             }catch (Exception e){
                 e.printStackTrace();
             }

             strReturn = new String(baDecryptData);

         }catch(Exception e){
             strReturn = strData;
         }

         return strReturn;

     }

     /**
      * 16진 문자열을 byte 배열로 변환한다.
      */
     public static byte[] hexToByteArray(String hex) {
         if (hex == null || hex.length() % 2 != 0) {
             return new byte[]{};
         }

         byte[] bytes = new byte[hex.length() / 2];
         for (int i = 0; i < hex.length(); i += 2) {
             byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
             bytes[(int) Math.floor(i / 2)] = value;
         }
         return bytes;
     }
     
    

     public static void main(String[] args) {
         String key = "Bar12345Bar12345"; // 128 bit key
         
         String enc = encrypt(key, "Hello AES");
         System.out.println(enc);
         System.out.println(decypt(key, enc));
     }
}
