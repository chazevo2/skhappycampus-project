package com.sk.idol.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utility {
	// 현재 날짜
	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd");
	}
	
	public static String getCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern, Locale.KOREA).format(new Date());
	}

	public static String getCurrentDate(String pattern, Locale locale) {
		return new SimpleDateFormat(pattern, locale).format(new Date());
	}
	
	public static java.sql.Date returnCurrentDate(String date) {
		return java.sql.Date.valueOf(date);
	}
	
	// 보안 문자
	public static StringBuilder secureCode = null;

	public static String getSecureCode() {
		StringBuilder secureCode = new StringBuilder("");
		int random = 0;
		for (int i = 0; i < 4; i++) {
			random = (int) (Math.random() * 10);
			secureCode.append(random);
		}
		return secureCode.toString();
	};
	
	public static String getSecureCode(int x) {
		StringBuilder secureCode = new StringBuilder("");
		int random = 0;
		for (int i = 0; i < x; i++) {
			random = (int) (Math.random() * 10);
			secureCode.append(random);
		}
		return secureCode.toString();
	};
	
	public static String getSecureCode2(int x) {
		StringBuilder secureCode = new StringBuilder("");
		Random random = new Random();
		int randomInt = 0;
		boolean b = false;
		char randomChar = 'a';
		for (int i = 0; i < x; i++) {
			b = random.nextBoolean();
			if(b) {
				randomInt = (int) (random.nextInt(10));
				secureCode.append(randomInt);
			} else {
				randomChar = (char)((int)(random.nextInt(26)) + 65);
				secureCode.append(randomChar);
			}
		}
		return secureCode.toString();
	};
	
	public static String getSecureCode3(int x) {
		StringBuilder secureCode = new StringBuilder("");
		Random random = new Random();
		int randomInt = 0;
		boolean b = false;
		char randomChar = 'a';
		for (int i = 0; i < x; i++) {
			b = random.nextBoolean();
			if(b) {
				randomInt = (int) (random.nextInt(10));
				secureCode.append(randomInt);
			} else {
				randomChar = (char)((int)(random.nextInt(26)) + 97);
				secureCode.append(randomChar);
			}
		}
		return secureCode.toString();
	};
}
