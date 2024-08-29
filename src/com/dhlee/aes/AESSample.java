package com.dhlee.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESSample {

	private AESSample() {
	}

	public static String encrypt(String strEncryptKey, String strData)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		strEncryptKey = strEncryptKey.substring(0, 16);
		byte[] bKey = strEncryptKey.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(bKey, "AES");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(bKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] baEncryptData = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			baEncryptData = cipher.doFinal(strData.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] encoded = Base64.getEncoder().encode(baEncryptData);
		return new String(encoded);
	}

	public static String decypt(String strDecryptKey, String strData)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		strDecryptKey = strDecryptKey.substring(0, 16);
		byte[] bKey = strDecryptKey.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(bKey, "AES");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(bKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] baDecryptData = null;

		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		baDecryptData = cipher.doFinal(Base64.getDecoder().decode(strData));
		return new String(baDecryptData);
	}

	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;
	}

	public static void main(String[] args) {
		String key = "Bar12345Bar12345"; // 128 bit key
		String enc;
		try {
			enc = encrypt(key, "Hello AES");
			System.out.println(enc);
			System.out.println(decypt(key, enc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
