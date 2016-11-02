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

	// validation password
	public static Boolean isEqualPasswords(String pass, String passRepeat) {

		if (pass.equals(passRepeat)) {
			return true;
		}
		return false;

	}

	// validation password
	public static Boolean checkDataFormat(String data) {
		RegExp regExp = RegExp.compile("[a-zA-Z]");
		boolean matchFound = regExp.test(data);

		if (!matchFound)
			return true;
		return false;

	}

	// validation only numbers

}
