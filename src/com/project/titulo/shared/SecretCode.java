package com.project.titulo.shared;

import java.util.Random;

public class SecretCode {

	protected String FORMAT = "QWERTYUIOPASDFGHJKLZXCVBNM987456321";

	protected String code = null;

	public SecretCode() {
		code = "";
	}

	// create a code from 6
	private String CreateCode() {

		@SuppressWarnings("unused")
		Random rand = new Random();
		for (int i = 0; i < 6; i++) {
			int max = FORMAT.length();
			int min = 0;
			int randomNum = min + (int) (Math.random() * ((max - min) + 1));

			code += FORMAT.charAt(randomNum);
		}
		return code;
	}

	public String getCode() {
		return CreateCode();
	}

}
