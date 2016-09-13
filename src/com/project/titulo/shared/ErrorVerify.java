package com.project.titulo.shared;

import com.google.gwt.user.client.Window;

public class ErrorVerify {
	
	public ErrorVerify(){}
	
	public static void getErrorAlert( String code){
		
		switch(code)
		{
			case "404":
				Window.alert("Missing website");
				break;
			case "offline":
				Window.alert("Sorry, no response from database.");
				break;
			case "banned":
				Window.alert("Sorry but this account is temporaly banned");
				break;
			case "wronguser":
				Window.alert("User or password don't exist.");
				break;
			case "tooshort":
				Window.alert("Login or Password is too short!");
				break;
			case "successadd":
				Window.alert("Successfully created");
				break;
			case "failadd":
				Window.alert("Creation failed");
				break;
			case "successdelete":
				Window.alert("Successfully deleted");
				break;
			case "faildelete":
				Window.alert("Delete failed");
				break;
			case "successupdate":
				Window.alert("Successfully updated");
				break;
			case "failupdate":
				Window.alert("Update failed");
				break;
			case "empty":
				Window.alert("Please fill in all fields correctly");
				break;
			case "fatal":
				Window.alert("Ups! somthing went bad");
				break;
			case "invalidmail":
				Window.alert("Invalid e-mail ");
				break;
			case "invalidpass":
				Window.alert("Invalid password");
				break;
			case "invalidfield":
				Window.alert("Invalid field");
				break;
			case "userexist":
				Window.alert("User already exist");
				break;
			case "usernoexist":
				Window.alert("User don't exist in the database");
				break;
			case "mailexist":
				Window.alert("E-mail already exist");
				break;
			case "mailnoexist":
				Window.alert("E-mail don't exist in the database");
				break;
			case "goodsendmail":
				Window.alert("We sent you a code to your e-mail");
				break;
			case "badsendmail":
				Window.alert("Fail sending code. Try again later");
				break;
			case "goodnewpass":
				Window.alert("Change pass successfully");
				break;
			case "badcode":
				Window.alert("Not a valid code for the user");
				break;
			case "noletters":
				Window.alert("Not letters allow");
				break;
			case "imagecreated":
				Window.alert("Image was created");
				break;
			case "imagefail":
				Window.alert("Image couldn't be created");
				break;
			case "nofileplot":
				Window.alert("There's no file selected to plot");
				break;
			case "nofilemetric":
				Window.alert("There's no file selected to use metric");
				break;
			default:
				Window.alert("Unknow error!!");
				break;
				
				

				
		}
		
	}
	
	public static String getError( String code){
		return null;	
	}

}