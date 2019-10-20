package com.hospital.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class HelperService {
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

	public Age calculateAge(Date birthDate)
	{
		int years = 0;
		int months = 0;
		int days = 0;

		//create calendar object for birth day
		Calendar birthDay = Calendar.getInstance();
		birthDay.setTimeInMillis(birthDate.getTime());

		//create calendar object for current day
		long currentTime = System.currentTimeMillis();
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(currentTime);

		//Get difference between years
		years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		int currMonth = now.get(Calendar.MONTH) + 1;
		int birthMonth = birthDay.get(Calendar.MONTH) + 1;

		//Get difference between months
		months = currMonth - birthMonth;

		//if month difference is in negative then reduce years by one
		//and calculate the number of months.
		if (months < 0)
		{
			years--;
			months = 12 - birthMonth + currMonth;
			if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
				months--;
		} else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
		{
			years--;
			months = 11;
		}

		//Calculate the days
		if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
			days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
		else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
		{
			int today = now.get(Calendar.DAY_OF_MONTH);
			now.add(Calendar.MONTH, -1);
			days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
		}
		else
		{
			days = 0;
			if (months == 12)
			{
				years++;
				months = 0;
			}
		}
		//Create new Age object
		return new Age(days, months, years);
	}
}
