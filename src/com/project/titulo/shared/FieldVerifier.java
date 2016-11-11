package com.project.titulo.shared;

import com.google.gwt.regexp.shared.RegExp;

public class FieldVerifier {

	// validation name
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}

		// Compile and use regular expression
		RegExp regExp = RegExp.compile("[a-zA-Z]");
		boolean matchFound = regExp.test(name);

		if (matchFound)
			return true;
		return false;
	}

	// validation email
	public static boolean isValidMail(String mail) {
		RegExp regExp = RegExp.compile("[a-zA-Z0-9_.]*@[a-zA-Z]*.[a-zA-Z]*");
		boolean matchFound = regExp.test(mail);

		if (matchFound)
			return true;
		return false;
	}

	// validation password
	public static Boolean isValidPass(String pass) {
		RegExp regExp = RegExp.compile("[a-zA-Z0-9]");
		boolean matchFound = regExp.test(pass);

		if (matchFound)
			return true;
		return false;

	}

	// validation equals password
	public static Boolean isEqualPasswords(String pass, String passRepeat) {

		if (pass.equals(passRepeat)) {
			return true;
		}
		return false;

	}

	// validation only letters
	public static Boolean isString(String text) {
		RegExp regExp = RegExp.compile("[a-zA-Z]");
		boolean matchFound = regExp.test(text);

		if (!matchFound)
			return true;
		return false;

	}
	
	//validation dimension files
	public static boolean checkDataDimension(int dimension, String data){
		int errorDimension = 0;
		String[] points = data.split("\n");
		for(String point:points){
			String[] axies = point.split("[;, ]");
			if(dimension!=axies.length)
			{
				errorDimension++;
			}
		}
		if(errorDimension>0)
			return true;
		return false;
	}
	
	
	
	
}
