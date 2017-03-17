package com.project.titulo.server.helpers;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileWritter {

	public static String Write(String realpath, String name, String resume){
		
		String fullpath = realpath + "/" +name.toLowerCase() +".txt";
		
		try (PrintStream out = new PrintStream(new FileOutputStream(fullpath))){
			out.print(resume);
			
		} catch (Exception e) {
			System.err.println("FileWritter: fail creating file");
			return e.toString();
		}
		
		return null;
	}
}
