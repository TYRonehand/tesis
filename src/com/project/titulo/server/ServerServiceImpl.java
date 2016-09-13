package com.project.titulo.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.project.titulo.client.ServerService;
import com.project.titulo.shared.SecretCode;
import com.project.titulo.shared.model.Answer;
import com.project.titulo.shared.model.ResumeTopic;
import com.project.titulo.shared.model.Topic;
import com.project.titulo.shared.model.User;
import com.project.titulo.shared.model.UserFile;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/*
importa java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
*/
/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServerServiceImpl extends RemoteServiceServlet implements ServerService 
{
	//datos conexion
	private Connection conn = null;
	//private String status;
	private String url = "jdbc:mysql://127.0.0.1:3306/proyectotitulo";
	private String user = "root";
	private String pass = "z-AoDaFII2Hp";
	
	//Constructor
	public ServerServiceImpl() {
		conn= CreateConn();
	}
	
	//create conection
	public Connection CreateConn(){
		Connection myconn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
		 	GWT.log(e.toString());
			e.printStackTrace();
		}
		
		try {
			myconn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) 
		{
		 	GWT.log(e.toString());
			e.printStackTrace();
		}
		
		return myconn;
	}
	
	/*--------------RPC FUNCTIONS---------------*/
	
	/*ADMIN-------------------------------------------------------------*/
	
	//get users list
	@Override
	public List<User> getUserList(String opcion) throws IllegalArgumentException 
	{
		List<User> allusers = new ArrayList<User>();
		 try 
		 {
			 String Query ="";
			 
			 if(opcion.equals("all"))
			 {
				 Query= "SELECT * FROM user ORDER BY iduser ASC";
			 }
			 else if(opcion.equals("old"))
			 {
				 Query= "SELECT * FROM user ORDER BY iduser DESC";
			 }
			 else
			 {
				 String[] words = opcion.split(" ");
				 Query= "SELECT * FROM user WHERE ";
				
				 if(words.length>=1)
					 Query+="name = %"+words[0]+"% ";		 
				 if(words.length>=2)
					 Query+="lastname = %"+words[1]+"% ";
				 
				 Query+= " ORDER BY iduser ASC";
			 }
			 
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 allusers.add(new User(	result.getString("iduser"), 
						 				result.getString("mail"), 
						 				result.getString("name"), 
						 				result.getString("lastname"), 
						 				result.getString("country"), 
						 				result.getString("ocupation"), 
						 				result.getString("web"),
						 				result.getString("university"), 
						 				"", 
						 				result.getString("registered"), 
						 				result.getString("creation"), 
						 				result.getString("banned"),
						 				""));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return allusers;
	}
	
	
	//user info validation
	@Override
	public User authenticateAdmin(String user, String pass) throws IllegalArgumentException
	{
		 User myadmin = null;
		 
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM administrator WHERE mail = '"+user.toUpperCase()+"' AND password = '"+pass+"' LIMIT 1";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 
				 myadmin = new User( result.getString("idadmin"), result.getString("mail"), result.getString("name"), 
						 			result.getString("lastname"), "", "Administrator", 
						 			"", "", "", "","", "false", "");
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return myadmin;
	}
	
	
	/*USER------------------------------------------------------------------------------*/
	//consulta si existe usuario
	@Override
	public Boolean userExist(String mail) throws IllegalArgumentException 
	{
		int count=0;
		try 
		 {
			 PreparedStatement ps = conn.prepareStatement("select mail from user where mail = '"+mail.toUpperCase()+"' limit 1");
			 ResultSet result = ps.executeQuery();
			 
			 while (result.next()) 
			 {
				 count++;
			 }
			 result.close();
			 ps.close();
			 
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		
		if(count==0){
			 return false;
		}
		return true;
	}
	

	//valida pin
	@Override
	public Boolean userRecovery(String mail,String PIN) throws IllegalArgumentException 
	{
		int count=0;
		try 
		 {
			 PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE mail = '"+mail.toUpperCase()+"' AND pin='"+PIN+"' limit 1");
			 ResultSet result = ps.executeQuery();
			 while (result.next()) 
			 {
				 count++;
			 }
			 result.close();
			 ps.close();
			 
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		
		if(count>0){
			eraseUserPin(mail);
			return true;
		}
		return false;
	}
	
	//user info validation
	@Override
	public User authenticateUser(String user, String pass) throws IllegalArgumentException
	{
		 User miuser = null;
		 
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM user WHERE mail = '"+user.toUpperCase()+"' AND password = '"+pass+"' LIMIT 1";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 String ban;
				 if(result.getBoolean("banned")) ban="true";
				 else ban="false";
				 
				 miuser = new User( result.getString("iduser"), result.getString("mail"), result.getString("name"), 
						 			result.getString("lastname"), result.getString("country"), result.getString("ocupation"), 
						 			result.getString("web"), result.getString("university"), "", result.getString("creation"),
						 			result.getString("registered"), ban, "");
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return miuser;
	}
	
	//user info no security
	@Override
	public User getUserInfo(String iduser) throws IllegalArgumentException {
		 User miuser = null;
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM user WHERE iduser = '"+iduser+"' LIMIT 1";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 miuser = new User( 
						 			result.getString("iduser"),
						 			result.getString("mail"), 
						 			result.getString("name"), 
						 			result.getString("lastname"), 
						 			result.getString("country"), 
						 			result.getString("ocupation"), 
						 			result.getString("web"), 
						 			result.getString("university"),
						 			"",
						 			result.getString("creation"),
						 			result.getString("registered"),
						 			result.getString("banned"),
						 			""
						 		  );
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return miuser;
	}
		
	//update info user
	@Override
	public Boolean setUserInfo(User myUser) throws IllegalArgumentException {

		 try 
		 {
			 //actualizamos usuario
			 String Query= " UPDATE user SET "
			 			 + "name='"+myUser.getName()+"', "
			 			 + "lastname='"+myUser.getLastname()+"', "
			 			 + "country='"+myUser.getCountry()+"', "
			 			 + "ocupation='"+myUser.getOcupation()+"', "
			 			 + "web='"+myUser.getWeb()+"',"
			 			 + "university='"+myUser.getUniversity()+"' "
			 			 + "WHERE iduser= "+myUser.getId()+"";
			 
			 //execute query
			 PreparedStatement ps = conn.prepareStatement(Query);
			 
			 ResultSet result = ps.executeQuery();
			 
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		 }
		 return true;
	}

	//add new user in database
	@Override
	public Boolean addUserInfo(User newUser) throws IllegalArgumentException {
		
		Boolean res= false;
		try 
		 {
			 //consultamos a base de datos
			 String Query= "INSERT INTO user (mail,name,lastname,country,password)"
			 			+"VALUES ('"+newUser.getMail().toUpperCase()+"','"+newUser.getName()+"','"+newUser.getLastname()+"','"+newUser.getCountry()+"','"+newUser.getPassword()+"');";
				
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ps.executeUpdate();
			 
			 //recorremos resultado
			 ps.close();
			 res= true;
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	res= false;
		 }
		return res;
	}
	
	//recovery account
	@Override
	public Boolean changeUserPassword(String mail, String password) throws IllegalArgumentException {
		try 
		{
			 //consultamos a base de datos
			String Query= " UPDATE user SET password = '"+password+"' WHERE mail='"+mail.toUpperCase()+"'";
			 
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 
			 //recorremos resultado
			 result.close();
			 ps.close();
			 
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		 }
		return true;
	}
	
	
	
	/*FORUM-TOPIC-------------------------------------------------------------------------*/
	//add new topic in database
	@Override
	public Boolean addNewTopic(Topic myTopic) throws IllegalArgumentException {
		
		try 
		 {
			//consultamos a base de datos
			 String Query= "INSERT INTO topic (title,description,iduser) VALUES ('"+myTopic.getTitle()+"','"+myTopic.getDescription()+"','"+myTopic.getIduser()+"')";
			 
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ps.executeUpdate();
			 
			 //recorremos resultado
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
			return false;
		 }
		return true;
	}
	
	//get soome topic
	@Override
	public Topic getTopic(String idtopic) throws IllegalArgumentException {
		Topic mytopic = null;
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM topic WHERE idtopic= '"+idtopic+"' LIMIT 1";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 mytopic = new Topic(   result.getString("idtopic"),
						 				result.getString("title"),
						 				result.getString("description"),
						 				result.getString("iduser"),
						 				result.getString("creation"),
						 				result.getString("enabled"),
						 				result.getString("edition"));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return mytopic;
	}
	
	//update topic
	@Override
	public Boolean setTopic(Topic myTopic) throws IllegalArgumentException {
		 try 
		 {
			 //actualizamos usuario
			 String Query= " UPDATE topic SET "
			 			 + "title='"+myTopic.getTitle()+"', "
			 			 + "description='"+myTopic.getDescription()+"', "
			 			 + "edition='"+myTopic.getEdition()+"' "
			 			 + "WHERE idtopic= "+myTopic.getIdtopic()+" ";
			 
			 //execute query
			 PreparedStatement ps = conn.prepareStatement(Query);
			 
			 ResultSet result = ps.executeQuery();
			 
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		 }
		 return true;
	}
	
	
	
	/*FORUM-ANSWER-------------------------------------------------------------------------*/
	//add new answer to topic
	@Override
	public Boolean addNewComment(Answer myAnswer) throws IllegalArgumentException {
		
		try 
		 {
			 //consultamos a base de datos
			 String Query= "INSERT INTO comentary(comentary,idtopic,iduser) VALUES ('"+myAnswer.getComentary()+"','"+myAnswer.getIdtopic()+"','"+myAnswer.getIduser()+"')";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ps.executeUpdate();
			 
			 //recorremos resultado
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
			return false;
		 }
		return true;
	}

	//get some comment
	@Override
	public List<Answer> getComments(String idtopic) throws IllegalArgumentException {
		
		List<Answer> myComments = new ArrayList<Answer>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "select * from comentary where idtopic="+idtopic+" order by creation asc";//"SELECT * FROM comentary WHERE idtopic= "+idtopic+" ORDER BY creation ASC";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 myComments.add(new Answer(	result.getString("idcomentary"),
							 				result.getString("idtopic"),
							 				result.getString("comentary"),
							 				result.getString("creation"),
							 				result.getString("modify"),
							 				result.getString("enabled"),
							 				result.getString("iduser")));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return myComments;
	}
	
	//set a comment
	@Override
	public Boolean setComment(Answer myAnswer) throws IllegalArgumentException {
		 try 
		 {
			 //actualizamos usuario
			 String Query= " UPDATE comentary SET "
			 			 + "comentary='"+myAnswer.getComentary()+"', "
			 			 + "enabled='"+myAnswer.getEnabled()+"' "
			 			 + "WHERE idcomentary = "+myAnswer.getIdcomentary()+" ";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		 }
		 return true;
	}
	
	
	/*FILES------------------------------------------------------------------------------*/
	//all user files 
	@Override
	public List<UserFile> getUserFiles(String user) throws IllegalArgumentException {

		 
		 List<UserFile> listFiles = new ArrayList<UserFile>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM datafile WHERE iduser = '"+user+"'";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //add element to list String Idfile, String Title, String Description, String Iduser, String Creation
				 listFiles.add(new UserFile(result.getString("iddatafile"), 
						 					result.getString("title"),
						 					result.getString("dimension"),
						 					result.getString("labelx"),
						 					result.getString("labely"),
						 					result.getString("labelz"),
								 			result.getString("description"),  
								 			result.getString("iduser"), 
								 			result.getString("creation"),
								 			result.getString("data")
								 			));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return listFiles;
	}
	
	//Get selected plot Files
	@Override
	public List<UserFile> getUserFilesPlot(String user) throws IllegalArgumentException {

		 List<UserFile> listFiles = new ArrayList<UserFile>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM plotselected WHERE iduser = '"+user+"'";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //add element to list String Idfile, String Title, String Description, String Iduser, String Creation
				 listFiles.add(new UserFile(result.getString("iddatafile"), 
						 					result.getString("title"),
						 					result.getString("dimension"),
						 					"",
						 					"",
						 					"",
								 			result.getString("description"),  
								 			result.getString("iduser"), 
								 			result.getString("creation"),
								 			result.getString("data")
								 			));
				 
				//create user files in directory files
			   	ServletContext context = this.getServletContext();
				try (PrintStream out = new PrintStream(new FileOutputStream(context.getRealPath("files")+"/"+result.getString("iddatafile")+".txt"))) 
				{
				    out.print(result.getString("data"));
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return listFiles;
	}
	
	//Get selected metric Files
	@Override
	public List<UserFile> getUserFilesMetric(String user) throws IllegalArgumentException {

		 
		 List<UserFile> listFiles = new ArrayList<UserFile>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM metricselected WHERE iduser = '"+user+"'";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //add element to list String Idfile, String Title, String Description, String Iduser, String Creation
				 listFiles.add(new UserFile(result.getString("iddatafile"), 
						 					result.getString("title"),
						 					result.getString("dimension"),
						 					"",
						 					"",
						 					"",
								 			result.getString("description"),  
								 			result.getString("iduser"), 
								 			result.getString("creation"),
								 			result.getString("data")
								 			));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return listFiles;
	}
	
	//select file plot
	@Override
	public Boolean addPlotFile(String iddatafile) throws IllegalArgumentException 
	{
		try 
		{
			 //actualizamos usuario
			 String Query= " UPDATE datafile SET plotselection = 1 WHERE iddatafile='"+iddatafile+"'";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
		} 
		catch (SQLException sqle) 
		{
			GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		}
		
		return true;
	}
	
	//remove file selected plot
	@Override
	public Boolean removePlotFile(String iddatafile)throws IllegalArgumentException 
	{
		try 
		{
			 //actualizamos usuario
			 String Query= " UPDATE datafile SET plotselection = 0 WHERE iddatafile='"+iddatafile+"'";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
		} 
		catch (SQLException sqle) 
		{
			GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		}
		return true;
	}
	
	//select metric
	@Override
	public Boolean addMetricFile(String iddatafile) throws IllegalArgumentException 
	{
		try 
		{
			 //actualizamos usuario
			 String Query= " UPDATE datafile SET metricselection = 1 WHERE iddatafile='"+iddatafile+"'";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
		} 
		catch (SQLException sqle) 
		{
			GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		}
		return true;
	}
	
	//remove metric selected
	@Override
	public Boolean removeMetricFile(String iddatafile)throws IllegalArgumentException 
	{
		try 
		{
			 //actualizamos usuario
			 String Query= " UPDATE datafile SET metricselection = 0 WHERE iddatafile='"+iddatafile+"'";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
		} 
		catch (SQLException sqle) 
		{
			GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 	return false;
		}
		return true;
	}
	
	//add file
	@Override
	public Boolean addNewFile(UserFile myFile) throws IllegalArgumentException {
		//return fail
		Boolean res = false;
		
        PreparedStatement pstmt = null;
		try 
		 {
            
            //consultamos a base de datos
			String Query= "INSERT INTO datafile (title,dimension,labelx,labely,labelz,description,iduser,data) VALUES ('"+myFile.getTitle()+"','"+myFile.getDimension()+"','"+myFile.getLabelx()+"','"+myFile.getLabely()+"','"+myFile.getLabelz()+"','"+myFile.getDescription()+"','"+myFile.getIduser()+"','"+myFile.getData()+"')";
			pstmt = conn.prepareStatement(Query);
			int state = pstmt.executeUpdate();
			//succes
			if(state==1) {
				res=true;
			}
			 pstmt.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
			res= false;
		 } 
		
		
		return res;
	}
	
	//delete file
	@Override
	public Boolean deleteFile(String iddatafile) throws IllegalArgumentException 
	{	
		try 
		 {
			 //consultamos a base de datos
			 String Query= "DELETE FROM datafile WHERE iddatafile ="+iddatafile+"";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 int state = ps.executeUpdate();
			 if(state==1)
			 {
				 File file = new File("./files/"+iddatafile+".txt");
				 file.delete();
			 }
			 //recorremos resultado
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
			return false;
		 }
		return true;
	}
	
	//get all resume topic 
	@Override
	public List<ResumeTopic> NewestResumeTopic() throws IllegalArgumentException {

		List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM resume_topic ORDER BY creation DESC";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //create  (String id,String title,String user,String total, Date created) 
				 //add element to list
				 resumetopic.add(new ResumeTopic(   result.getString("idtopic"), 
							 						result.getString("title"),
									 				result.getString("iduser"),
									 				result.getString("name"), 
									 				result.getString("total"),  
									 				result.getString("creation")
						 		  					));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return resumetopic;
	}
	
	//get all resume topic 
	@Override
	public List<ResumeTopic> OldestResumeTopic() throws IllegalArgumentException {

		List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM resume_topic ORDER BY creation ASC";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //create  (String id,String title,String user,String total, Date created) 
				 //add element to list
				 resumetopic.add(new ResumeTopic(   result.getString("idtopic"), 
							 						result.getString("title"),
									 				result.getString("iduser"),
									 				result.getString("name"), 
									 				result.getString("total"),  
									 				result.getString("creation")
						 		  					));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return resumetopic;
	}	
	
	//get all resume topic 
	@Override
	public List<ResumeTopic> MyResumeTopic( String iduser) throws IllegalArgumentException {

		List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
		 try 
		 {
			 //consultamos a base de datos
			 String Query= "SELECT * FROM resume_topic WHERE iduser = '"+iduser+"' ORDER BY creation ASC";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //create  (String id,String title,String user,String total, Date created) 
				 //add element to list
				 resumetopic.add(new ResumeTopic(   result.getString("idtopic"), 
							 						result.getString("title"),
									 				result.getString("iduser"),
									 				result.getString("name"), 
									 				result.getString("total"),  
									 				result.getString("creation")
						 		  					));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return resumetopic;
	}	
	
	//get all resume topic 
	@Override
	public List<ResumeTopic> SearchResumeTopic(String specialword) throws IllegalArgumentException {

		List<ResumeTopic> resumetopic = new ArrayList<ResumeTopic>();
		 try 
		 {
			 //consultamos a base de datos SELECT * FROM table WHERE Column LIKE '%test%';
			 String Query= "SELECT * FROM resume_topic WHERE title LIKE '%"+specialword+"%' ORDER BY creation DESC";
			 PreparedStatement ps = conn.prepareStatement(Query);
			 ResultSet result = ps.executeQuery();
			 //recorremos resultado
			 while (result.next()) 
			 {
				 //create  (String id,String title,String user,String total, Date created) 
				 //add element to list
				 resumetopic.add(new ResumeTopic(   result.getString("idtopic"), 
							 						result.getString("title"),
									 				result.getString("iduser"),
									 				result.getString("name"), 
									 				result.getString("total"),  
									 				result.getString("creation")
						 		  					));
			 }
			 result.close();
			 ps.close();
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		 }
		 return resumetopic;
	}
	
	//clean pin ffrom user
	
	//get pin to user and Send email verify for lost password
	@Override
	public Boolean sendEmailVerify(String email) throws IllegalArgumentException {

		SecretCode code = new SecretCode();
		String CODE = code.getCode();
		Boolean valid = false;
		//set user PIN
		try 
		 {
			 //actualizamos usuario
			 String Query= " UPDATE user SET pin = '"+CODE+"' WHERE mail='"+email.toUpperCase()+"'";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
			valid=true;//can send email
		 } 
		 catch (SQLException sqle) 
		 {
		 	GWT.log(sqle.toString());
		 	sqle.printStackTrace();

			valid=false;//somthing bad
		 }
		//true send email
		if(valid)
		{
			return SendEmail(email, "cagutierrez@ing.ucsc.cl", "You have a petition to change your passwor.", "<h1>Petition for new password</h1> <p>Hi there! we recently receive a petition for lost password. that's why we sent you this recup code ["+CODE+"]. Under any reason that you didn't ask it just delete this email. have a nice day!!</p>");
			//true it was sended and all ok - false problem
		}
		return valid;
			
	}
	
	//Live Plot
	@Override
	public Boolean LivePlot(String iduser, List<String> idfiles) throws IllegalArgumentException 
	{
		//servlet context to find path
   	 	ServletContext context = this.getServletContext();
		//files configuration
		List<UserFile> files = this.getUserFilesPlot(iduser);
		
		//set command for terminal
		String commandTerminal = "set terminal canvas jsdir './js/'  mousing;"
								+"set output '"+context.getRealPath("plots")+"/"+iduser+".html';"
								+"set autoscale;";
		//plot files
		int cont = 0;
		commandTerminal +="plot ";
		for(UserFile f:files)
		{
			commandTerminal +=" '"+context.getRealPath("files")+"/"+f.getIddatafile()+".txt' title '"+f.getTitle()+"' with dots";
			if(cont<files.size()-1)
				commandTerminal +=",";
			cont++;
		}
		return GnuplotLoad.setCommand(commandTerminal,context);
		
	}
	
	//create image in 2d-------------------------------------------------------------------------------------------------------
	@Override
	public String CreateImage2D(Boolean points,Boolean lines, String fileFormat,String title, List<String> labelxyz, List<String> limitxyz, String iduser) throws IllegalArgumentException 
	{
		//servlet context to find path
   	 	ServletContext context = this.getServletContext();
   	 	
		//files configuration
		List<UserFile> files = this.getUserFilesPlot(iduser);
		
		//if exist selected files
		if(files.size()>0)
		{	
			String direction="";
			
			//set command 
			String  commandTerminal ="";
			
			//set terminal format
			if(fileFormat.equals("png"))
			{
				commandTerminal += "set terminal pngcairo;";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".png';";
				//save direction
				direction = "images/"+iduser+".png";
			}
			else if(fileFormat.equals("svg"))
			{
				commandTerminal += "set terminal svg;";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".svg';";
				//save direction
				direction = "images/"+iduser+".svg";
			}
			else if(fileFormat.equals("eps"))
			{
				commandTerminal += "set terminal postscript eps enhanced color font 'Helvetica,10';";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".eps';";
				//save direction
				direction = "images/"+iduser+".eps";
			}
			else if(fileFormat.equals("pdf"))
			{
				commandTerminal += "set terminal pdf;";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".pdf';";
				//save direction
				direction = "images/"+iduser+".pdf";
			}
			else if(fileFormat.equals("html"))
			{
				commandTerminal += "set terminal canvas jsdir './js/'  mousing;";
				commandTerminal +="set output '"+context.getRealPath("plots")+"/"+iduser+".html';";
				//save direction
				direction ="plots/"+iduser+".html";
			}
			//common config
			commandTerminal +="set autoscale;";
			commandTerminal +="set title '"+title+"';";
			commandTerminal +="set xlabel '"+labelxyz.get(0)+"';";
			commandTerminal +="set ylabel '"+labelxyz.get(1)+"';";
			
			//limits minx maxx miny maxy minz maxz
			if(limitxyz.size()==6)
			{
				//limit min max for X
				if(limitxyz.get(0).isEmpty() && limitxyz.get(1).isEmpty()){
					commandTerminal +="set xrange [*:*];";
				}else{
					commandTerminal +="set xrange ["+limitxyz.get(0)+":"+limitxyz.get(1)+"];";
				}
				//limit min max for Y
				if(limitxyz.get(2).isEmpty() && limitxyz.get(3).isEmpty()){
					commandTerminal +="set yrange [*:*];";
				}else{
					commandTerminal +="set yrange ["+limitxyz.get(2)+":"+limitxyz.get(3)+"];";
				}
			}
			
			//add points and lines style to plot
			String pointlineadding = " with ";
			if(lines && points)
				pointlineadding+=" linesp lt 1 pt 3 ";
			if(lines && !points)
				pointlineadding+=" lines  ";
			if(!lines && points)
				pointlineadding+=" points ps 1 ";
			if(!lines && !points)
				pointlineadding+=" dots ";
			
			//add plot files
			int cont = 0;
			commandTerminal +="plot ";
			for(UserFile f:files)
			{
				commandTerminal +=" '"+context.getRealPath("files")+"/"+f.getIddatafile()+".txt' title '"+f.getTitle()+"' "+pointlineadding;
				if(cont<files.size()-1)
					commandTerminal +=",";
				cont++;
			}
			commandTerminal +=";";		
					
			//Load gnuplot
			Boolean state = GnuplotLoad.setCommand(commandTerminal,context);
			if(state)
				return direction;//send direction of file
		}
		return null;
			
	}
	
	
	//create image in 3d-------------------------------------------------------------------------------------------------------
	@Override
	public String CreateImage3D(Boolean points,Boolean lines, String fileFormat, String title,  List<String> labelxyz, List<String> limitxyz, String iduser) throws IllegalArgumentException 
	{
		//servlet context to find path
   	 	ServletContext context = this.getServletContext();
   	 	
		//files configuration
		List<UserFile> files = this.getUserFilesPlot(iduser);
		
		//if exist selected files
		if(files.size()>0)
		{	
			String direction="";
			
			//set command 
			String  commandTerminal ="";
			
			//set terminal format
			if(fileFormat.equals("png"))
			{
				commandTerminal += "set terminal pngcairo;";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".png';";
				//save direction
				direction = "images/"+iduser+".png";
			}
			else if(fileFormat.equals("svg"))
			{
				commandTerminal += "set terminal svg;";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".svg';";
				//save direction
				direction = "images/"+iduser+".svg";
			}
			else if(fileFormat.equals("eps"))
			{
				commandTerminal += "set terminal postscript eps enhanced color font 'Helvetica,10';";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".eps';";
				//save direction
				direction = "images/"+iduser+".eps";
			}
			else if(fileFormat.equals("pdf"))
			{
				commandTerminal += "set terminal pdf;";
				commandTerminal +="set output '"+context.getRealPath("images")+"/"+iduser+".pdf';";
				//save direction
				direction = "images/"+iduser+".pdf";
			}
			else if(fileFormat.equals("html"))
			{
				commandTerminal += "set terminal canvas jsdir './js/'  mousing;";
				commandTerminal +="set output '"+context.getRealPath("plots")+"/"+iduser+".html';";
				//save direction
				direction ="plots/"+iduser+".html";
			}
			
			//title and labels
			commandTerminal +="set autoscale;";
			commandTerminal +="set title '"+title+"';";
			commandTerminal +="set xlabel '"+labelxyz.get(0)+"';";
			commandTerminal +="set ylabel '"+labelxyz.get(1)+"';";
			commandTerminal +="set zlabel '"+labelxyz.get(2)+"';";
			
			//limits minx maxx miny maxy minz maxz
			if(limitxyz.size()==6)
			{
				//limit min max for X
				if(limitxyz.get(0).isEmpty() && limitxyz.get(1).isEmpty()){
					commandTerminal +="set xrange [*:*];";
				}else{
					commandTerminal +="set xrange ["+limitxyz.get(0)+":"+limitxyz.get(1)+"];";
				}
				//limit min max for Y
				if(limitxyz.get(2).isEmpty() && limitxyz.get(3).isEmpty()){
					commandTerminal +="set yrange [*:*];";
				}else{
					commandTerminal +="set yrange ["+limitxyz.get(2)+":"+limitxyz.get(3)+"];";
				}
				//limit min max for Z
				if(limitxyz.get(4).isEmpty() && limitxyz.get(5).isEmpty()){
					commandTerminal +="set zrange [*:*];";
				}else{
					commandTerminal +="set zrange ["+limitxyz.get(4)+":"+limitxyz.get(5)+"];";
				}
			}
			
			//add points and lines style to plot
			String pointlineadding = " with ";
			if(lines && points)
				pointlineadding+=" pm3d at t";
			if(lines && !points)
				pointlineadding+=" pm3d at t";
			if(!lines && points)
				pointlineadding+=" pm3d at t";
			if(!lines && !points)
				pointlineadding+=" pm3d at t";
			
			//add plot files
			int cont = 0;
			commandTerminal +="splot ";
			for(UserFile f:files)
			{
				commandTerminal +=" '"+context.getRealPath("files")+"/"+f.getIddatafile()+".txt' title '"+f.getTitle()+"' "+pointlineadding;
				if(cont<files.size()-1)
					commandTerminal +=",";
				cont++;
			}
			commandTerminal +=";replot;";		
					
			//Load gnuplot
			Boolean state = GnuplotLoad.setCommand(commandTerminal,context);
			if(state)
				return direction;//send direction of file
		}
		return null;
			
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* functions properly from server */
	
	//send email
	private Boolean SendEmail(String to, String from, String Subjettext, String Messagehtml)
	{
		return true;
		/*
		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(Subjettext);

	         // Send the actual HTML message, as big as you like
	         message.setContent(Messagehtml, "text/html" );

	         // Send message
	         Transport.send(message);
		}
		catch (MessagingException mex) 
		{
			return false;
		}
		return true;
		*/
	}
	//clear pin from user
	private void eraseUserPin(String email)
	{
		//set user PIN
		try 
		{
			 //actualizamos usuario
			 String Query= " UPDATE user SET pin = '' WHERE mail='"+email.toUpperCase()+"'";
			//execute query
			PreparedStatement ps = conn.prepareStatement(Query);
			ResultSet result = ps.executeQuery();
			 
			result.close();
			ps.close();
		} 
		catch (SQLException sqle) 
		{
			GWT.log(sqle.toString());
		 	sqle.printStackTrace();
		}
	}

	
}//end class
