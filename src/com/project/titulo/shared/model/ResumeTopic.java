package com.project.titulo.shared.model;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ResumeTopic implements Serializable 
{
	private  String idtopic;
    private  String title;
    private  String user;
    private  String iduser;
    private  String total;
    private  String created;
	public ResumeTopic(){
		   idtopic="";
	       title="";
	       user="";
	       total="";
	       created="";
	}
    
    public ResumeTopic(String id,String title,String iduser, String user,String total, String created) {
       this.idtopic = id;
       this.title = title;
       this.iduser=iduser;
       this.user = user;
       this.total = total;
       this.created = created;
    }

	public String getIdtopic() {
		return idtopic;
	}

	public String getTitle() {
		return title;
	}

	public String getUser() {
		return user;
	}

	public String getTotal() {
		return total;
	}

	public String getCreated() {
		return created;
	}

	public String getIduser() {
		return iduser;
	}

    
} 