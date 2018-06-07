package com.dhlee.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSASample {

	public RSASample() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {

	try {
        //����Ű �� ����Ű ����
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        Key publicKey = keyPair.getPublic(); // ����Ű
        Key privateKey = keyPair.getPrivate(); // ����Ű
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

        //System.out.println("public key modulus(" + publicKeySpec.getModulus() + ") exponent(" + publicKeySpec.getPublicExponent() + ")");
        //System.out.println("private key modulus(" + privateKeySpec.getModulus() + ") exponent(" + privateKeySpec.getPrivateExponent() + ")");

        
        // ��ȣȭ(Encryption) ����
        String inputStr = "������123"; // "������123"�� ��ȣȭ�Ѵ�!

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] arrCipherData = cipher.doFinal(inputStr.getBytes()); // ��ȣȭ�� ������(byte �迭)
        String strCipher = new String(arrCipherData);
        
        System.out.println(strCipher); // ��ȣȭ ����� ���(������ ���̱⿡�� ���� �� ����)

        
        // ��ȣȭ(Decryption) ����
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] arrData = cipher.doFinal(arrCipherData);
        String strResult = new String(arrData);

        System.out.println(strResult); // ��ȣȭ ����� ���(�ٽ� "������123"�� ��µ�)
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}


