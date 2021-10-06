package com.dhlee.des;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
	
public class Simple3DesTest {
	private static String ENCODE = "utf-8";
	
	public Simple3DesTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws Exception {
        String text = "java12345";

        String enen = encrypt(text);
        String dede = decrypt(enen);

        System.out.println("\nOrigin key: " + key());
        System.out.println("\nOrigin text: " + text);
        System.out.println("\nEncrypted text: " + enen);
        System.out.println("\nDecrypted text: " + dede);
    }
    
    /**
     * ����Ű ����
     * @return
     */
    public static String key()
    {
//        return "ab_booktv_abcd09";
        return "ab_booktv_abcd0912345678";
    }
    
    /**
     * Ű��
     * 24����Ʈ�� ��� TripleDES �ƴϸ� DES
     * @return
     * @throws Exception
     */
    public static Key getKey() throws Exception {
        return (key().length() == 24) ? getKey2(key()) : getKey1(key());
    }

    /**
     * ������ ���Ű�� ������ ���� �޼��� (DES)
     * require Key Size : 16 bytes
     *
     * @return Key ���Ű Ŭ����
     * @exception Exception
     */
    public static Key getKey1(String keyValue) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
    }
    
    /**
     * ������ ���Ű�� ������ ���� �޼��� (TripleDES)
     * require Key Size : 24 bytes
     * @return
     * @throws Exception
     */
    public static Key getKey2(String keyValue) throws Exception {
        DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
    }

    /**
     * ���ڿ� ��Ī ��ȣȭ
     *
     * @param ID
     *            ���Ű ��ȣȭ�� ����ϴ� ���ڿ�
     * @return String ��ȣȭ�� ID
     * @exception Exception
     */
    public static String encrypt(String ID) throws Exception {
        if (ID == null || ID.length() == 0)
            return "";
        
        String instance = (key().length() == 24) ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getKey());
        
        String amalgam = ID;

        byte[] inputBytes1 = amalgam.getBytes(ENCODE);
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String outputStr1 = encoder.encode(outputBytes1);
        return outputStr1;
    }

    /**
     * ���ڿ� ��Ī ��ȣȭ
     *
     * @param codedID
     *            ���Ű ��ȣȭ�� ����ϴ� ���ڿ�
     * @return String ��ȣȭ�� ID
     * @exception Exception
     */
    public static String decrypt(String codedID) throws Exception {
        if (codedID == null || codedID.length() == 0)
            return "";
        
        String instance = (key().length() == 24) ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey());
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

        byte[] inputBytes1 = decoder.decodeBuffer(codedID);
        byte[] outputBytes2 = cipher.doFinal(inputBytes1);

        String strResult = new String(outputBytes2, ENCODE);
        return strResult;
    }
}
