package com.dhlee.aes;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesCryptoSample {
	public static final String defaultCharset = "utf-8";

	private AesCryptoSample() {
		
	}

	public static String encryptAES(String key, String text) {
		return encrypt(key, text, "AES");
	}

	public static String decryptAES(String key, String base64EncodedText) {
		return decrypt(key, base64EncodedText, "AES");
	}

	public static String encrypt(String key, String text, String algoritm) throws IllegalArgumentException {
		try {
			SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), algoritm);
			Cipher cipher = Cipher.getInstance(algoritm);
			cipher.init(Cipher.ENCRYPT_MODE, secureKey);
			byte[] encryptData = cipher.doFinal(text.getBytes(defaultCharset));
			return new String(Base64.getEncoder().encode(encryptData));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.toString());
		}
	}

	public static String decrypt(String key, String text, String algoritm) throws IllegalArgumentException {
		try {
			SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), algoritm);
			byte[] encryptedData = Base64.getDecoder().decode(text.getBytes(defaultCharset));
			Cipher cipher = Cipher.getInstance(algoritm);
			cipher.init(Cipher.DECRYPT_MODE, secureKey);
			byte[] plainText = cipher.doFinal(encryptedData);
			return new String(plainText);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.toString());
		}
	}

	public static void main(String[] args) {
		String key = "Bar12345Bar12345";
		String text = "Hello AES ÇÑ±Û";
		System.out.println(text);
		String enc = encryptAES(key, text);
		System.out.println(enc);
		System.out.println(decryptAES(key, enc));
	}
}
