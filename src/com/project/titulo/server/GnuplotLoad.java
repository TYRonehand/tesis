package com.project.titulo.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GnuplotLoad implements IsSerializable {

	// get actual os name
	private static String OS = System.getProperty("os.name").toLowerCase();
	// constructor
	public GnuplotLoad(){}
	// windows?
	private static boolean isWindows() { return (OS.indexOf("win") >= 0);}
	// Mac?
	private static boolean isMac() {return (OS.indexOf("mac") >= 0);}
	// linux?
	private static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}
	// solaris?
	private static boolean isSolaris() {return (OS.indexOf("sunos") >= 0);}
	
	//get directory
	public static String getDirectory(ServletContext context) {
		if (isWindows()) {
			return context.getRealPath("/gnuplot/win/bin/gnuplot.exe");
		} else if (isMac()) {
			return "/usr/local/bin/gnuplot";
		} else if (isUnix()) {
			return "/usr/bin/gnuplot";
		} else if (isSolaris()) {
			return "/usr/sbin/gnuplot";
		} else {
			return null;
		}
	}

	//load gnuplot
	public static Boolean setCommand(String commandTerminal, ServletContext context) {
		System.err.println("gnuplot command:" + commandTerminal);
		// server - GNUPLOT DIRECTORY
		String[] s = { getDirectory(context), "-e", commandTerminal };
		// execute GNUPLOT aplication and send commandterminal
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(s);
			InputStream stdin = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.err.println("gnuplot:" + line);
			
			int exitVal = proc.waitFor();
			if (exitVal != 0)
				System.err.println("gnuplot Process exitValue: " + exitVal);
			proc.getInputStream().close();
			proc.getOutputStream().close();
			proc.getErrorStream().close();
		} catch (Exception e) {
			System.err.println("Fail: " + e);
			return false;// fail
		}
		return true;//ok
	}

}
