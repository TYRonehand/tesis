package com.project.titulo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.titulo.shared.model.Answer;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.ResumeTopic;
import com.project.titulo.shared.model.Topic;
import com.project.titulo.shared.model.User;
import com.project.titulo.shared.model.UserFile;

/**
 * The async counterpart of <code>ServiceService</code>.
 */
public interface ServerServiceAsync {
	
	//admin
	void getUserList(String opcion, AsyncCallback<List<User>> callback) throws IllegalArgumentException;
	
	//user
	void userExist(String mail, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void authenticateUser(String user, String pass, AsyncCallback<User>	callback) throws IllegalArgumentException;
	
	void getUserInfo(String user, AsyncCallback<User>	callback) throws IllegalArgumentException;

	void setUserInfo(User myUser, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void addUserInfo(User myUser, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void userRecovery(String mail, String PIN, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void changeUserPassword(String mail, String password, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void deleteUserInfo(String iduser, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	
	//files
	void getUserFiles(String user, AsyncCallback<List<UserFile>> callback) throws IllegalArgumentException;

	void addNewFile(UserFile myFile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void deleteFile(String iddatafile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getUserFilesPlot(String user, AsyncCallback<List<UserFile>> callback) throws IllegalArgumentException;
	
	void addPlotFile(String iddatafile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void removePlotFile(String iddatafile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getUserFilesMetric(String user, AsyncCallback<List<UserFile>> callback) throws IllegalArgumentException;
	
	void addMetricFile(String iddatafile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void removeMetricFile(String iddatafile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getCountUserFiles(String iduser, AsyncCallback<Integer> callback) throws IllegalArgumentException;
	
	//topic
	void addNewTopic(Topic myTopic, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getTopic(String idtopic, AsyncCallback<Topic> callback) throws IllegalArgumentException;
	
	void setTopic(Topic mytopic, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void NewestResumeTopic( AsyncCallback<List<ResumeTopic>> callback) throws IllegalArgumentException;
	
	void OldestResumeTopic( AsyncCallback<List<ResumeTopic>> callback) throws IllegalArgumentException;
	
	void MyResumeTopic(String iduser, AsyncCallback<List<ResumeTopic>> callback) throws IllegalArgumentException;
	
	void SearchResumeTopic(String specialword, AsyncCallback<List<ResumeTopic>> callback) throws IllegalArgumentException;
	

	//comments
	void addNewComment(Answer myAnswer, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getComments(String idcomentary, AsyncCallback<List<Answer>> callback) throws IllegalArgumentException;
	
	void setComment(Answer myAnswer, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	//user verify
	void sendEmailVerify(String email, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	/*------------PLOT---------------*/
	
	//live plot
	void LivePlot(String iduser,List<String> idfiles, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	//create image
	void CreateImage2D(Boolean points,Boolean lines, String fileFormat, String title, List<String> labelxyz, List<String> limitxyz, String iduser, AsyncCallback<String> callback) throws IllegalArgumentException;
	void CreateImage3D(Boolean points,Boolean lines, String fileFormat, String title, List<String> labelxyz, List<String> limitxyz, String iduser, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	/*------------METRICS---------------*/

	void CalculateER(String iduser, AsyncCallback<List<MetricResults>> callback) throws IllegalArgumentException;
	void CalculateSP(String iduser, AsyncCallback<List<MetricResults>> callback) throws IllegalArgumentException;
	void CalculateGD(String iduser, AsyncCallback<List<MetricResults>> callback) throws IllegalArgumentException;
	void CalculateC(String iduser, AsyncCallback<List<MetricResults>> callback) throws IllegalArgumentException;
	void CalculateE(String iduser, AsyncCallback<List<MetricResults>> callback) throws IllegalArgumentException;
	
	/*------------EXPORT RESULTS---------------*/
	void ExportResults(String iduser, List<MetricResults> Results, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
}
