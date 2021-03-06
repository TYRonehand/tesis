package com.project.titulo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.titulo.shared.model.AdminChartResume;
import com.project.titulo.shared.model.AdminResume;
import com.project.titulo.shared.model.Answer;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.ResumeTopic;
import com.project.titulo.shared.model.SubAnswer;
import com.project.titulo.shared.model.Topic;
import com.project.titulo.shared.model.User;
import com.project.titulo.shared.model.UserFile;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface ServerService extends RemoteService {

	/* ADMIN */
	//dashboar resume info
	AdminResume getResumeInfo() throws IllegalArgumentException;
	
	//dashboar chart resume users
	AdminChartResume getChartUsers() throws IllegalArgumentException;
	
	//dashboar chart resume topics
	AdminChartResume getChartTopics() throws IllegalArgumentException;
	
	
	
	// list of users
	List<User> getUserList(String opcion) throws IllegalArgumentException;

	// autenticacion
	User authenticateAdmin(String user, String pass)
			throws IllegalArgumentException;
	
	/* USER */

	// recovery account
	Boolean userRecovery(String mail, String PIN)
			throws IllegalArgumentException;

	// change pass
	Boolean changeUserPassword(String mail, String password)
			throws IllegalArgumentException;

	// consultar existencia usuario
	Boolean userExist(String mail) throws IllegalArgumentException;

	// autenticacion
	User authenticateUser(String user, String pass)
			throws IllegalArgumentException;

	// get info
	User getUserInfo(String user) throws IllegalArgumentException;

	// set info
	Boolean setUserInfo(User myUser) throws IllegalArgumentException;

	// insert user
	Boolean addUserInfo(User newUser) throws IllegalArgumentException;

	// erase user
	Boolean deleteUserInfo(String iduser) throws IllegalArgumentException;

	/* FILES */
	// get a datafile
	UserFile getDataFile(String iddatafile) throws IllegalArgumentException;

	// found user files
	List<UserFile> getUserFiles(String user) throws IllegalArgumentException;

	// add user file
	Boolean addNewFile(UserFile myFile) throws IllegalArgumentException;

	// update user file
	Boolean setFile(UserFile myFile) throws IllegalArgumentException;

	// delete user file
	Boolean deleteFile(String iddatafile) throws IllegalArgumentException;

	// found user files selected
	List<UserFile> getUserFilesPlot(String user)
			throws IllegalArgumentException;

	Boolean addPlotFile(String iddatafile) throws IllegalArgumentException;

	Boolean removePlotFile(String iddatafile) throws IllegalArgumentException;

	// found user files selected
	List<UserFile> getUserFilesMetric(String user)
			throws IllegalArgumentException;

	Boolean addMetricFile(String iddatafile) throws IllegalArgumentException;

	Boolean removeMetricFile(String iddatafile) throws IllegalArgumentException;

	// number of files from user
	Integer getCountUserFiles(String iduser) throws IllegalArgumentException;

	/* TOPIC */
	// new topic
	Boolean addNewTopic(Topic myTopic) throws IllegalArgumentException;

	// get topic
	Topic getTopic(String idtopic) throws IllegalArgumentException;

	// set info
	Boolean setTopic(Topic myTopic) throws IllegalArgumentException;

	// all resume topic
	List<ResumeTopic> NewestResumeTopic() throws IllegalArgumentException;

	// all resume topic
	List<ResumeTopic> OldestResumeTopic() throws IllegalArgumentException;

	// all resume topic
	List<ResumeTopic> MyResumeTopic(String iduser)
			throws IllegalArgumentException;

	// all resume topic
	List<ResumeTopic> SearchResumeTopic(String specialword)
			throws IllegalArgumentException;

	/* COMMENTS */
	// new coment
	Boolean addNewComment(Answer myAnswer) throws IllegalArgumentException;

	// get coment
	List<Answer> getComments(String idcomentary)
			throws IllegalArgumentException;

	// set coment
	Boolean setComment(Answer myAnswer) throws IllegalArgumentException;
	

	/* SUB-COMMENTS */
	// new coment
	Boolean addNewSubComment(SubAnswer mySubAnswer) throws IllegalArgumentException;

	// get coment
	List<SubAnswer> getSubComments(String idcomentary) throws IllegalArgumentException;

	// set coment
	Boolean setSubComment(SubAnswer mySubAnswer) throws IllegalArgumentException;


	/*------------PLOT---------------*/


	// create image
	String CreateImage2D(Boolean points, Boolean lines, String fileFormat,
			String title, List<String> labelxyz, List<String> limitxyz,
			String iduser) throws IllegalArgumentException;

	String CreateImage3D(Boolean points, Boolean lines, String fileFormat,
			String title, List<String> labelxyz, List<String> limitxyz,
			String iduser) throws IllegalArgumentException;

	/*------------METRICS---------------*/

	List<MetricResults> CalculateER(String iduser)
			throws IllegalArgumentException;

	List<MetricResults> CalculateSP(String iduser)
			throws IllegalArgumentException;

	List<MetricResults> CalculateHA(String iduser)
			throws IllegalArgumentException;
	
	List<MetricResults> CalculateHR(String iduser)
			throws IllegalArgumentException;
	
	List<MetricResults> CalculateGD(String iduser)
			throws IllegalArgumentException;

	List<MetricResults> CalculateC(String iduser)
			throws IllegalArgumentException;

	List<MetricResults> CalculateE(String iduser)
			throws IllegalArgumentException;

	List<MetricResults> CalculateGNVG(String iduser)
			throws IllegalArgumentException;
	/*------------EXPORT RESULTS---------------*/
	Boolean ExportResults(String iduser, List<MetricResults> Results)
			throws IllegalArgumentException;

	// SEND EMAIL VERIFY
	String sendEmailVerify(String email) throws IllegalArgumentException;
}
