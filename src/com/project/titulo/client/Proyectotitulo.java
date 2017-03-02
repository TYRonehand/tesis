package com.project.titulo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.project.titulo.shared.CookieVerify;
import com.project.titulo.shared.GoToUrl;

/**
 * 
 * @author Carlos Alfredo Gutierrez Acevedo
 * @version 2.0.1
 * 
 */
public class Proyectotitulo implements EntryPoint {
	
	private CookieVerify mycookie=new CookieVerify(false);
	private GoToUrl url = new GoToUrl();
	
	public void onModuleLoad() 
	{
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			   @Override
			   public void onValueChange(ValueChangeEvent<String> event) {
			       String historyToken = event.getValue();
				   //Window.alert("History token change "+historyToken);
				   TokenLoader(historyToken);
			   }   
			});
		
		if(Cookies.getCookieNames().size()<=1){
			 mycookie=new CookieVerify(true);
		}
		
		if(!mycookie.getCookieIdurl().isEmpty() && !mycookie.getCookieUser().isEmpty())
			TokenLoader(mycookie.getCookieIdurl().toLowerCase());
		else{
				TokenLoader(History.getToken());
		}
		
		
	}
	/**
	 * Validate the token in the url exist and load the correct place in the website.
	 * @param historyToken This parameter is a token used to recognize where is located de user in the website.
	 * @return this dont return anything just load the correspondig widgets.
	 */
	private void TokenLoader(String historyToken){
		CookieVerify mycookieaux=new CookieVerify(false);
		
		//token known
		if(historyToken.length()>1)
		{	
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
				    || historyToken.equals("admindashboard")  
				    || historyToken.equals("admin")    
				    || historyToken.equals("users")  
				    || historyToken.contains(History.encodeHistoryToken("uid=")))
			{
				//recovery account and administration login
			    if(historyToken.equals("recovery") || historyToken.equals("admin"))
			    {
				    url.GoTo(historyToken, null, null);
			    }
			    else//anyone else
			    {
					    //cookie user exist => user loged
					    if(mycookieaux.getCookieUser().length()>0)
					    {
					    	//viewprofile id user
					    	if(historyToken.contains("uid="))
						    {
					    		//profile view
						    	String iduser = historyToken.substring(4);
						    	url.GoTo("user", iduser, null);
						    }else{
						    	//user user
						    	String IDUSER = mycookieaux.getCookieUser();
						    	url.GoTo(historyToken, IDUSER, mycookieaux.getCookieTopic());
						    }
					    }else if(mycookieaux.getCookieUser().length()<=0){
						    url.GoTo("login", null, null);
					    }else{
						    //empty user
					    	History.newItem("login");
					    }
				    	
			    }
		    }else{
		    	//token unknown
			    //Window.alert("404");
			    url.GoTo("404", null, null);
		    }
		}else{
			Window.Location.replace(GWT.getHostPageBaseURL()+"#login");
			
		}
	}

}
