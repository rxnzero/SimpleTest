package com.dhlee.des;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PublicKeyPrivateKeySample {

	public PublicKeyPrivateKeySample() {
		// TODO Auto-generated constructor stub
	}
	
	public byte[] readFileBytes(String filename) throws IOException
	{
	    Path path = Paths.get(filename);
	    return Files.readAllBytes(path);        
	}

	public PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePublic(publicSpec);       
	}

	public PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePrivate(keySpec);     
	}
	
	public byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
	    cipher.init(Cipher.ENCRYPT_MODE, key);  
	    return cipher.doFinal(plaintext);
	}
	
	public String encryptString(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] encrypted = encrypt(key, plaintext);  
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();        
        String base64Str = encoder.encode(encrypted);
        return base64Str;
	}
	
	public byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
	    cipher.init(Cipher.DECRYPT_MODE, key);  
	    return cipher.doFinal(ciphertext);
	}
	
	public String decryptString(PrivateKey key, String ciphertext) throws Exception
	{
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] cipherBytes = decoder.decodeBuffer(ciphertext);
        
		byte[] decrypted = decrypt(key, cipherBytes);  
	    return new String(decrypted, "UTF8");
	}

	public void HelloKeyFile()
	{
	    try
	    {
	        PublicKey publicKey = readPublicKey("public.der");
	        PrivateKey privateKey = readPrivateKey("private.der");
	        byte[] message = "Hello World".getBytes("UTF8");
	        byte[] secret = encrypt(publicKey, message);
	        byte[] recovered_message = decrypt(privateKey, secret);
	        System.out.println(new String(recovered_message, "UTF8"));
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public void HelloKeyObject()
	{
	    try
	    {
	    	 KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
	         generator.initialize(1024);
	     
	         KeyPair keyPair = generator.genKeyPair();
	         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	     
	         PublicKey publicKey = keyPair.getPublic();
	         PrivateKey privateKey = keyPair.getPrivate();
	         
	         String message = "Hello World입니다.";
	         System.out.println("Origin : " + message);
	         byte[] srcBytes = message.getBytes("UTF8");
	        
	        String secret = encryptString(publicKey, srcBytes);
	        System.out.println("encryptString : " + secret);
	        
	        String recovered = decryptString(privateKey, secret);
	        System.out.println("decryptString : " + recovered);	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public void HelloKeyObjectString()
	{
	    try
	    {
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
	         System.out.println("publicKeyModulus = " + publicKeyModulus);
	         System.out.println("publicKeyExponent = " + publicKeyExponent);
//	         String publicK = Base64.encodeBase64String(publicKey.getEncoded());
//	         String privateK = Base64.encodeBase64String(privateKey.getEncoded());
//	         byte[] publicBytes = Base64.decodeBase64(publicK);
//	         X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
//	         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	         PublicKey pubKey = keyFactory.generatePublic(keySpec);
	         
	         // generate PublicKey
	         BigInteger pModulus = new BigInteger(publicKeyModulus, 16);
	         BigInteger pExponent = new BigInteger(publicKeyExponent, 16);	         
	         RSAPublicKeySpec keySpec = new RSAPublicKeySpec(pModulus, pExponent);
	         PublicKey publicKeyNew = keyFactory.generatePublic(keySpec);
	         
	         String message = "Hello World입니다. 키복원";
	         System.out.println("Origin : " + message);
	         byte[] srcBytes = message.getBytes("UTF8");
	        
	        String secret = encryptString(publicKeyNew, srcBytes);
	        System.out.println("encryptString : " + secret);
	        
	        String recovered = decryptString(privateKey, secret);
	        System.out.println("decryptString : " + recovered);	        
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		PublicKeyPrivateKeySample sample = new PublicKeyPrivateKeySample();
		sample.HelloKeyObject();
		sample.HelloKeyObjectString();
	}
}
