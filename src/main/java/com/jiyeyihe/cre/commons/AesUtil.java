package com.jiyeyihe.cre.commons;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {
	private String appid;
	private String sessionKey;
	
	public AesUtil(String appid, String sessionKey) {
		super();
		this.appid = appid;
		this.sessionKey = sessionKey;
	} 
		
	public String decryptData(String content, String iv){  
		try {
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding"); 
			SecretKeySpec key_spec = new SecretKeySpec(Base64.decodeBase64(this.sessionKey), "AES");
			IvParameterSpec wiv = new IvParameterSpec(Base64.decodeBase64(iv));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, wiv); 
			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(content); 
			// 解密
			byte[] original = cipher.doFinal(encrypted);  
			byte[] bytes = PKCS7Encoder.decode(original);
			String res = new String(original,"utf-8");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	} 

	/**
	 * 代码中的测试数据和微信小程序官方提供的一致
	 * @param args
	 */
	public static void main(String[] args) {
		String appid = "wx5e547afa55712fc0";
		String sessionKey = "E61wk1BqFvJIcdqayYj3fA==";
		
		AesUtil util = new AesUtil(appid, sessionKey);
		String encryptedData = "uwX1UxYESd2hO7TcxM7hAPknHPzfVmTXuCCB5xfmrUuGKzaiW2q/C1rL75RY9NWdyeqtaASRAXQurVWnAggAphdqZdtgHhYKK2+CN26QJibu2m33abJ/ImzTMKww6/0U5BKu7mEm6rbVdbSxfvWlQV4ud1tKBY0aayG2Rmi29xMUZklVqQU5TTKbAGKbN06jXt837DInSWwJeVmDb8TUPQ==";
		String iv = "B8vSJ4UDD6uB0s+FRr12QA==";
		System.out.println(util.decryptData(encryptedData, iv));
	}
	
}
