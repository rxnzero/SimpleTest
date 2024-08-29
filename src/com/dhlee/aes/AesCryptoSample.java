package com.dhlee.aes;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesCryptoSample {

	private AesCryptoSample() {
		
	}

	public static String encryptAES(String key, String text) {
		return encrypt(key, text, "AES");
	}

	public static String decryptAES(String key, String text) {
		return decrypt(key, text, "AES");
	}

	public static String encrypt(String key, String text, String algoritm) throws IllegalArgumentException {
		try {
			SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), algoritm);
			Cipher cipher = Cipher.getInstance(algoritm);
			cipher.init(Cipher.ENCRYPT_MODE, secureKey);
			byte[] encryptData = cipher.doFinal(text.getBytes());
			byte temp[] = Base64.getEncoder().encode(encryptData);
			return new String(temp);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.toString());
		}
	}

	public static String decrypt(String key, String text, String algoritm) throws IllegalArgumentException {
		try {
			SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), algoritm);
			byte[] encryptedData = Base64.getDecoder().decode(text);
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
		String enc = encryptAES(key, "Hello AES");
		System.out.println(enc);
		System.out.println(decryptAES(key, enc));
	}
}
