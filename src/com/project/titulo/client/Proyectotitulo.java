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
	
	private CookieVerify mycookieaux=new CookieVerify(false);
	private GoToUrl url = new GoToUrl();
	
	public void onModuleLoad() 
	{
		
		//handler
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			   @Override
			   public void onValueChange(ValueChangeEvent<String> event) {
			       String historyToken = event.getValue();
				   //Window.alert("History token change "+historyToken);
				   TokenLoader(historyToken);
			   }   
			});
		
		//no cookies created
		if(Cookies.getCookieNames().size()==0){
			mycookieaux=new CookieVerify(true);
			 TokenLoader(mycookieaux.getCookieIdurl().toLowerCase());
		}else{
			//there's cookies created
			if(!mycookieaux.getCookieIdurl().isEmpty()){
				//url
				TokenLoader(mycookieaux.getCookieIdurl().toLowerCase());
				
			}else{
				//empty cookies or no user conected
				mycookieaux.delCookiesInfo();
				
			}
		}
		
	}
	
	/**
	 * Validate the token in the url exist and load the correct place in the website.
	 * @param historyToken This parameter is a token used to recognize where is located de user in the website.
	 * @return this dont return anything just load the correspondig widgets.
	 */
	private void TokenLoader(String historyToken){
		
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
			    || historyToken.equals("404")  
			    || historyToken.contains(History.encodeHistoryToken("uid=")))
			{
				//recovery account and administration login
			    if(historyToken.equals("recovery") || historyToken.equals("admin") || historyToken.equals("login") || historyToken.equals("404"))
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
						    	
						    }else if(!historyToken.isEmpty()){
						    	//user user
						    	String IDUSER = mycookieaux.getCookieUser();
						    	url.GoTo(historyToken, IDUSER, mycookieaux.getCookieTopic());
						    }
					    	
					    }else{
					    	//cookie user not exist => user not loged
							Window.Location.replace(GWT.getHostPageBaseURL()+"#login");
					    }
			    }
		    }else{
		    	//token unknown
				Window.Location.replace(GWT.getHostPageBaseURL()+"#404");
		    }
		}else{
			Window.Location.replace(GWT.getHostPageBaseURL()+"#login");
		}
	}

}
