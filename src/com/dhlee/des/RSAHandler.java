package com.dhlee.des;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAHandler {
    
    public static Map<String,Object> encRsa() throws Exception{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
    
        KeyPair keyPair = generator.genKeyPair();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
    
        // 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
        RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
    
        String publicKeyModulus = publicSpec.getModulus().toString(16);
        String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
    
        Map<String,Object> map = new HashMap<String,Object>();
        
        map.put("__rsaPrivateKey__", privateKey);
        map.put("publicKeyModulus", publicKeyModulus);
        map.put("publicKeyExponent", publicKeyExponent);
        
        System.out.println("__rsaPrivateKey__:"+privateKey);
        System.out.println("publicKeyModulus:"+publicKeyModulus);
        System.out.println("publicKeyExponent:"+publicKeyExponent);
        
        return map;
    }
    
    
    public static String decRsa(PrivateKey privateKey , String securedValue) throws Exception{        
        System.out.println("will decrypt : " + securedValue);
        String strReturn = securedValue;
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            byte[] encryptedBytes = hexToByteArray(securedValue);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            strReturn = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
            System.out.println(strReturn);    
        }catch(Exception e){
            
        }
        
        return strReturn;
    }
    
    
    public static String decRsa(Map<String,Object> map) throws Exception{
        String strReturn = "";
        PrivateKey privateKey = (PrivateKey) map.get("__rsaPrivateKey__");
        String securedValue = (String)map.get("testRsa");
        
        System.out.println("will decrypt : " + securedValue);
        
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
        System.out.println(decryptedValue);    
        
        return decryptedValue;
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
    
    public static void main(String[] args) throws Exception {
    	Map rsaMap = RSAHandler.encRsa();
    	try{
            
//            session.setAttribute("RSA" , rsaMap.get("__rsaPrivateKey__") );
//            session.setAttribute("THREE_DEPTH_KEY" , "!!!!!!!!!!!!!!!!!!!!!!!!");  //기본값
                        
//            System.out.println("SESSION ID:" + session.getId());
            StringBuffer sBuf = new StringBuffer();
            sBuf.append("{");
            sBuf.append("'RSA':'" + rsaMap.get("__rsaPrivateKey__") + "'" );
            sBuf.append("'THREE_DEPTH_KEY':'" + "!!!!!!!!!!!!!!!!!!!!!!!!" + "'" );
            sBuf.append("'KEY_MODULE':'" + rsaMap.get("publicKeyModulus") + "'" );
            sBuf.append(",'KEY_EXPON':'" + rsaMap.get("publicKeyExponent") + "'");
            sBuf.append("}");
            
            System.out.println(sBuf.toString());
//            response.getWriter().write(sBuf.toString());
//            response.flushBuffer();
        }catch(Exception e){
            System.out.println("error:"+e);
        }
    	
    	
    	try{            
            String str3desRsa = ""; //request.getParameter("THREE_DEPTH"); 
            PrivateKey privateKey = (PrivateKey)rsaMap.get("__rsaPrivateKey__");// (PrivateKey) request.getSession().getAttribute("RSA");
    
//            request.getSession().setAttribute("THREE_DEPTH_KEY" , RSAHandler.decRsa(privateKey , str3desRsa) );
            System.out.println("THREE_DEPTH_KEY=" + RSAHandler.decRsa(privateKey , str3desRsa) );
//            response.getWriter().write("ok");
//            response.flushBuffer();
        }catch(Exception e){
            System.out.println("error:"+e);
        }
    }
}
