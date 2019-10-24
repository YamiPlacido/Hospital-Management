package com.hospital.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomService {
	public String createRandomPassword() {
		String pass = "";
		char c;
		Random rand = new Random();
		int min = 33;
		int max = 126;
		for (int i = 0; i < 6; i++) {
			int randomNum = rand.nextInt((max - min) + 1) + min;
			c =(char)randomNum;
			pass += c;
		}
		System.out.println(pass);
	    return pass;
	}
	
	public String createRandomCodeNumber(int fixedLength) {
		
		String code = "";
		char c;
		Random rand = new Random();
		int min = 48;
		int max = 57;
		for (int i = 0; i < fixedLength; i++) {
			int randomNum = rand.nextInt((max - min) + 1) + min;
			c =(char)randomNum;
			code += c;
		}
		System.out.println(code);
	    return code;
	}
}
