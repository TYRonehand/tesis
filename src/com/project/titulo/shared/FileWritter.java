package com.project.titulo.shared;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileWritter {

	public static Boolean Write(String realpath, String name, String resume){
		
		String fullpath = realpath + "/" +name.toLowerCase() +".txt";
		
		try (PrintStream out = new PrintStream(new FileOutputStream(fullpath))){
			out.print(resume);
			
		} catch (Exception e) {
			EmailAlert.WarningEmail("filewritter.java - Write", "Message: "+e.toString());
			System.err.println("FileWritter: fail creating file");
			return false;
		}
		
		return true;
	}
}
