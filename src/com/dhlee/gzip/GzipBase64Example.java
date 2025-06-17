package com.dhlee.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipBase64Example {
private static final String DEFAULT_CAHRSET = "UTF-8";
	
	public static void main(String[] args) {
		String originalString = "압축하고 인코딩할 문자열입니다.";

		try {
			String base64EncodedData = compress(originalString);

			System.out.println("Original String: " + originalString);
			System.out.println("Base64 Encoded String: " + base64EncodedData);

			// Base64 디코딩 후 gzip 압축 해제
			String decodedString = decompress(base64EncodedData);

			System.out.println("Decoded String: " + decodedString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String compress(String plainText) throws IOException {
		if (plainText == null || plainText.isEmpty()) {
			return null;
		}

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
				gzipOutputStream.write(plainText.getBytes(DEFAULT_CAHRSET));
			}
			byte[] compressedData = byteArrayOutputStream.toByteArray();
			return Base64.getEncoder().encodeToString(compressedData);
		}
	}

	public static String decompress(String base64EncodedData) throws IOException {

		if (base64EncodedData == null || base64EncodedData.length() == 0) {
			return null;
		}
		byte[] compressedData = Base64.getDecoder().decode(base64EncodedData);

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
					GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {

				byte[] buffer = new byte[1024];
				int len;
				while ((len = gzipInputStream.read(buffer)) > 0) {
					byteArrayOutputStream.write(buffer, 0, len);
				}
			}
			return byteArrayOutputStream.toString(DEFAULT_CAHRSET);
		}
	}
}
