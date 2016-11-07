package com.project.titulo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.project.titulo.shared.CookieVerify;

//Entry Point
public class Proyectotitulo implements EntryPoint {
	// cookie
	private CookieVerify mycookie=new CookieVerify(true);
	private GoToUrl url = new GoToUrl();
	
	public void onModuleLoad() 
	{
		//change token event
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			   @Override
			   public void onValueChange(ValueChangeEvent<String> event) {
			       String historyToken = event.getValue();
				   //Window.alert("History token change "+historyToken);
				   TokenLoader(historyToken);
			   }   
			});
		
		//is token in url
		if(!History.getToken().isEmpty())
			TokenLoader(History.getToken());
		else//no token
			History.newItem("login");
		
	}
	
	private void TokenLoader(String historyToken){
		//token known
		if(historyToken.equals("home") 
				|| historyToken.equals("files") 
			    || historyToken.equals("plot") 
			    || historyToken.equals("metric")  
			    || historyToken.equals("forum")  
			    || historyToken.equals("faq")  
			    || historyToken.equals("topic") 
			    || historyToken.equals("login")
			    || historyToken.equals("recovery")  
			    || historyToken.equals("profile")   
			    || historyToken.equals("admin")  
			    || historyToken.contains(History.encodeHistoryToken("uid=")))
		{
			//recovery account
		    if(historyToken.equals("recovery"))
		    {
			    url.GoTo(historyToken, null, null);
		    }
		    else//anyone else
		    {
			    if(Cookies.getCookieNames().isEmpty())
			    {
				    //no cookie
				    Window.Location.reload();
			    }else{
					    //cookie
					    if(!mycookie.getCookieUser().isEmpty())
					    {
					    	//viewprofile id user
					    	if(historyToken.contains("uid="))
						    {
					    		//profile view
						    	String iduser = historyToken.substring(4);
						    	url.GoTo("user", iduser, null);
						    }else{
						    	//exist user
						    	url.GoTo(historyToken, mycookie.getCookieUser(), mycookie.getCookieTopic());
						    }
					    }else{
						    //empty user
						    url.GoTo("login", null, null);
					    }
			    	}
			    
		    }
	   }else{
		   //token unknown
		   //Window.alert("404");
		   url.GoTo("404", null, null);
	   }
	   
	}

}
