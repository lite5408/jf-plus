package com.jf.plus.common.utils;

import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.jf.plus.common.core.Constants;

public class YSTUtils {

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	// 验证签名代码
	public static Boolean VerifyData(String data, String token) {
		try {
			byte[] plainTextByte = data.getBytes();
			byte[] signatureByte = ConvertHexToBytes(token);
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			Certificate cert = factory.generateCertificate(resourceLoader.getResource(Constants.SSOPKIDEV).getInputStream());
			Signature signatureChecker = Signature.getInstance("SHA1withRSA");
			signatureChecker.initVerify(cert);
			signatureChecker.update(plainTextByte);
			if (signatureChecker.verify(signatureByte))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static byte[] ConvertHexToBytes(String value) {
		int len = value.length() / 2;
		byte[] ret = new byte[len];
		for (int i = 0; i < len; i++) {
			ret[i] = (byte) (Integer.parseInt(value.substring(i * 2, (i + 1) * 2), 16));
		}
		return ret;
	}

	public static void main(String[] args) {
		String data = "eyJ5c3RJRCI6IjAzMzIzNCIsInNhcElEIjoiMDAyNzA2NjMiLCJuYW1lIjoi5r2Y5Lyf5am3Iiwib3JnSUQiOiIxMTk5OTUiLCJvcmdOYW1lIjoi6Zu25ZSu6LSi5a+M5a6kIiwicGF0aElEIjoiLzEwMjI1OC8xMTQ5NzUvMTAyMzk3LzExOTk5NSIsInBhdGhOYW1lIjoi5q2m5rGJ5YiG6KGML+mbtuWUrumHkeiejeS6i+S4mumDqC/pm7bllK7ph5Hono3kuovkuJrpg6jpu4Tnn7PliIbpg6gv6Zu25ZSu6LSi5a+M5a6kIiwicGxhdGZvcm0iOiJhbnBob25lIiwib3NWZXJzaW9uIjoiNy4wIiwicHViVmVyc2lvbiI6IlYyLjMuOSIsImRldmljZUlEIjoiQTY1NDJDOEEtQ0VBNS00NkFDLUE4NUYtMkIwNkE4MDNBNjVEIiwidGltZSI6IjIwMTgtMDEtMjVUMTE6NDI6MDMifQ==";
		String token = "48279eecb7e23f0c5924808fe37897e7e7a6a8a30109e91bf2a4c5f491aa202ab69ba8ce797af6a7ea88dbdb6d6d442f841ea0bddac24d48ae02b527220e1059219e7bed15aaaf90fb5c5df3c1c54e982be25d21e76ccafc3554181ae688a872c1d9c92d014d10665252846657c3e2de7ac6c9446685ad22bef2f74b52c9f864";
		
		if(!VerifyData(data, token)){
			System.out.println("签名错误");
		}
//		String jsonStr = Encodes.decodeBase64String(data);
//		System.out.println(jsonStr);
//		AouthUser user = JSON.parseObject(jsonStr, AouthUser.class);
//		System.out.println(user.getSapID());
	}

}
