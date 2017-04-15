package com.vnsoftware.util;

import java.security.MessageDigest;
import java.util.Formatter;

import com.google.gxp.com.google.common.io.MessageDigestAlgorithm;

public class SecurityHelper {
	public static String endcodeSHA256(String text) {
		MessageDigest m = MessageDigestAlgorithm.SHA_256.get();
		m.update(text.getBytes());
		byte[] endCode = m.digest();
		String a = hexEncode(endCode);
		return a;
	}

	private static String hexEncode(byte buf[]) {
		StringBuilder sb = new StringBuilder();
		final Formatter formatter = new Formatter(sb);
		for (int i = 0; i < buf.length; i++) {
			formatter.format("%02x", buf[i]);
		}
		formatter.close();
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(SecurityHelper.endcodeSHA256("admin"));
	}
}
