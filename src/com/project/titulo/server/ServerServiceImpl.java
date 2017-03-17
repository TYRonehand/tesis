package com.project.titulo.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.project.titulo.client.ServerService;
import com.project.titulo.server.crud.Admin_crud;
import com.project.titulo.server.crud.Comment_crud;
import com.project.titulo.server.crud.DataFile_crud;
import com.project.titulo.server.crud.SubComment_crud;
import com.project.titulo.server.crud.Topic_crud;
import com.project.titulo.server.crud.User_crud;
import com.project.titulo.server.helpers.ExportDataResult;
import com.project.titulo.server.helpers.GnuplotLoad;
import com.project.titulo.server.metrics.ErrorRatio;
import com.project.titulo.server.metrics.GNVG;
import com.project.titulo.server.metrics.GenDistance;
import com.project.titulo.server.metrics.HyperArea;
import com.project.titulo.server.metrics.HyperAreaRatio;
import com.project.titulo.server.metrics.Spacing;
import com.project.titulo.shared.model.AdminChartResume;
import com.project.titulo.shared.model.AdminResume;
import com.project.titulo.shared.model.Answer;
import com.project.titulo.shared.model.MetricResults;
import com.project.titulo.shared.model.ResumeTopic;
import com.project.titulo.shared.model.SubAnswer;
import com.project.titulo.shared.model.Topic;
import com.project.titulo.shared.model.User;
import com.project.titulo.shared.model.UserFile;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ServerServiceImpl extends RemoteServiceServlet implements
		ServerService {

	// Constructor
	public ServerServiceImpl() {

	}

	/*--------------RemoteProceduralControl FUNCTIONS---------------*/

	/* ADMIN------------------------------------------------------------- */
	
	// get resumen db
	@Override
	public AdminResume getResumeInfo() throws IllegalArgumentException {

		System.err.println("Function: getResumeInfo");

		return Admin_crud.ResumeInfo();
	}

	// get last 6 month user chart
	@Override
	public AdminChartResume getChartUsers() throws IllegalArgumentException {

		return Admin_crud.ChartUsers();
	}

	// get last 6 month topic chart
	@Override
	public AdminChartResume getChartTopics() throws IllegalArgumentException {

		System.err.println("Function: getChartTopics");

		return Admin_crud.ChartTopics();
	}

	// get users list
	@Override
	public List<User> getUserList(String opcion) throws IllegalArgumentException {

		System.err.println("Function: getUserList");

		return User_crud.FindUserList(opcion);
	}

	// user info validation
	@Override
	public User authenticateAdmin(String user, String pass) throws IllegalArgumentException {

		System.err.println("Function: authenticateAdmin");

		return Admin_crud.authenticateAdmin(user, pass);
	}

	
	/*
	 * USER----------------------------------------------------------------------
	 */
	// consulta si existe usuario
	@Override
	public Boolean userExist(String mail) throws IllegalArgumentException {

		System.err.println("Function: userExist");

		return User_crud.Exist(mail);
	}

	// valida pin
	@Override
	public Boolean userRecovery(String mail, String PIN) throws IllegalArgumentException {

		System.err.println("Function: userRecovery");

		return User_crud.userRecovery(mail, PIN);
	}

	// user info validation
	@Override
	public User authenticateUser(String user, String pass)
			throws IllegalArgumentException {

		System.err.println("Function: authenticateUser");

		return User_crud.authenticateUser(user, pass);
	}

	// user info no security
	@Override
	public User getUserInfo(String iduser) throws IllegalArgumentException {

		System.err.println("Function: getUserInfo");

		return User_crud.read(iduser);
	}

	// update info user
	@Override
	public Boolean setUserInfo(User myUser) throws IllegalArgumentException {

		System.err.println("Function: setUserInfo");

		return User_crud.update(myUser);
	}

	// add new user in database
	@Override
	public Boolean addUserInfo(User newUser) throws IllegalArgumentException {

		System.err.println("Function: addUserInfo");

		return User_crud.create(newUser);
	}

	// recovery account
	@Override
	public Boolean changeUserPassword(String mail, String password)
			throws IllegalArgumentException {

		System.err.println("Function: changeUserPassword");

		return User_crud.changeUserPassword(mail, password);
	}

	// delete a user

	@Override
	public Boolean deleteUserInfo(String iduser)
			throws IllegalArgumentException {

		System.err.println("Function: deleteUserInfo");
		
		return User_crud.delete(iduser);
	}

	// get pin to user and Send email verify for lost password
	@Override
	public String sendEmailVerify(String email) throws IllegalArgumentException {

		System.err.println("Function: sendEmailVerify");

		return User_crud.sendEmailVerify(email);
	}

	/*
	 * FORUM-TOPIC----------------------------------------------------------------
	 * ---------
	 */
	// add new topic in database
	@Override
	public Boolean addNewTopic(Topic myTopic) throws IllegalArgumentException {

		System.err.println("Function: addNewTopic");

		return Topic_crud.create(myTopic);
	}

	// get soome topic
	@Override
	public Topic getTopic(String idtopic) throws IllegalArgumentException {

		System.err.println("Function: getTopic");

		return Topic_crud.read(idtopic);
	}

	// update topic
	@Override
	public Boolean setTopic(Topic myTopic) throws IllegalArgumentException {

		System.err.println("Function: setTopic");

		return Topic_crud.update(myTopic);
	}

	// get all resume topic
	@Override
	public List<ResumeTopic> NewestResumeTopic() throws IllegalArgumentException {

		System.err.println("Function: NewestResumeTopic");

		return Topic_crud.NewestResumeTopic();
	}

	// get all resume topic
	@Override
	public List<ResumeTopic> OldestResumeTopic() throws IllegalArgumentException {

		System.err.println("Function: OldestResumeTopic");

		return Topic_crud.OldestResumeTopic();
	}

	// get all resume topic
	@Override
	public List<ResumeTopic> MyResumeTopic(String iduser) throws IllegalArgumentException {

		System.err.println("Function: MyResumeTopic");

		return Topic_crud.MyResumeTopic(iduser);
	}

	// get all resume topic
	@Override
	public List<ResumeTopic> SearchResumeTopic(String specialword) throws IllegalArgumentException {

		System.err.println("Function: SearchResumeTopic");

		return Topic_crud.SearchResumeTopic(specialword);
	}

	/*
	 * FORUM-ANSWER--------------------------------------------------------------
	 * -----------
	 */
	// add new answer to topic
	@Override
	public Boolean addNewComment(Answer myAnswer) throws IllegalArgumentException {

		System.err.println("Function: addNewComment");

		return Comment_crud.create(myAnswer);
	}

	// get some comment
	@Override
	public List<Answer> getComments(String idtopic) throws IllegalArgumentException {

		System.err.println("Function: getComments");

		return Comment_crud.read(idtopic);
	}

	// set a comment
	@Override
	public Boolean setComment(Answer myAnswer) throws IllegalArgumentException {

		System.err.println("Function: setComment");

		return Comment_crud.update(myAnswer);
	}

	/*
	 * FORUM-SUB-ANSWER----------------------------------------------------------
	 * ----
	 */
	// add new answer to topic
	@Override
	public Boolean addNewSubComment(SubAnswer mySubComment) throws IllegalArgumentException {

		System.err.println("Function: addNewSubComment");

		return SubComment_crud.create(mySubComment);
	}

	// get some comment
	@Override
	public List<SubAnswer> getSubComments(String idcomment) throws IllegalArgumentException {

		System.err.println("Function: getSubComments");

		return SubComment_crud.read(idcomment);
	}

	// set a comment
	@Override
	public Boolean setSubComment(SubAnswer myAnswer) throws IllegalArgumentException {

		System.err.println("Function: setSubComment");

		return SubComment_crud.update(myAnswer);
	}

	/*
	 * FILES----------------------------------------------------------------------
	 */
	// add new file
	@Override
	public Boolean addNewFile(UserFile myFile) throws IllegalArgumentException {

		System.err.println("Function: addNewFile");

		return DataFile_crud.create(myFile);
	}

	// update a file
	@Override
	public Boolean setFile(UserFile myFile) throws IllegalArgumentException {

		System.err.println("Function: setFile");

		return DataFile_crud.update(myFile);
	}

	// delete a file
	@Override
	public Boolean deleteFile(String iddatafile) throws IllegalArgumentException {

		System.err.println("Function: deleteFile");

		return DataFile_crud.delete(iddatafile);
	}

	// a file data
	@Override
	public UserFile getDataFile(String iddatafile) throws IllegalArgumentException {

		System.err.println("Function: getDataFile");

		return DataFile_crud.read(iddatafile);
	}

	// all user files
	@Override
	public List<UserFile> getUserFiles(String iduser) throws IllegalArgumentException {

		System.err.println("Function: getUserFiles");

		return DataFile_crud.getUserFiles(iduser);
	}

	// get count of user files
	@Override
	public Integer getCountUserFiles(String iduser) throws IllegalArgumentException {

		System.err.println("Function: getCountUserFiles");

		return DataFile_crud.getCountUserFiles(iduser);

	}

	// select file for plot
	@Override
	public Boolean addPlotFile(String iddatafile) throws IllegalArgumentException {

		System.err.println("Function: addPlotFile");

		return DataFile_crud.addPlotFile(iddatafile);
	}

	// remove plot file selected
	@Override
	public Boolean removePlotFile(String iddatafile) throws IllegalArgumentException {

		System.err.println("Function: removePlotFile");

		return DataFile_crud.removePlotFile(iddatafile);
	}

	// select files for metric
	@Override
	public Boolean addMetricFile(String iddatafile) throws IllegalArgumentException {

		System.err.println("Function: addMetricFile");

		return DataFile_crud.addMetricFile(iddatafile);
	}

	// remove metric files selected
	@Override
	public Boolean removeMetricFile(String iddatafile) throws IllegalArgumentException {

		System.err.println("Function: removeMetricFile");

		return DataFile_crud.removeMetricFile(iddatafile);
	}

	// Get selected plot Files
	@Override
	public List<UserFile> getUserFilesPlot(String iduser) throws IllegalArgumentException {

		System.err.println("Function: getUserFilesPlot");
		
		// create user files in directory files
		ServletContext context = this.getServletContext();
		String path = context.getRealPath("files");
		
		return DataFile_crud.getUserFilesPlot(iduser, path);
	}

	// Get selected metric Files
	@Override
	public List<UserFile> getUserFilesMetric(String iduser) throws IllegalArgumentException {

		System.err.println("Function: getUserFilesMetric");

		return DataFile_crud.getUserFilesMetric(iduser);
	}

	/*
	 * PLOT-FILES---------------------------------------------------------------------------
	 */

	// 2Dimension---------------------------------------------------------------------
	@Override
	public String CreateImage2D(Boolean points, Boolean lines,
			String fileFormat, String title, List<String> labelxyz,
			List<String> limitxyz, String iduser)
			throws IllegalArgumentException {

		System.err.println("Function: CreateImage2D");

		// servlet context to find path
		ServletContext context = this.getServletContext();

		// files configuration
		List<UserFile> files = this.getUserFilesPlot(iduser);

		// if exist selected files
		if (files.size() > 0) {
			String direction = "";

			// set command
			String commandTerminal = "";

			// set terminal format
			if (fileFormat.equals("png")) {
				commandTerminal += "set terminal pngcairo;";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".png';";
				// save direction
				direction = "images/" + iduser + ".png";
			} else if (fileFormat.equals("svg")) {
				commandTerminal += "set terminal svg;";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".svg';";
				// save direction
				direction = "images/" + iduser + ".svg";
			} else if (fileFormat.equals("eps")) {
				commandTerminal += "set terminal postscript eps enhanced color font 'Helvetica,10';";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".eps';";
				// save direction
				direction = "images/" + iduser + ".eps";
			} else if (fileFormat.equals("pdf")) {
				commandTerminal += "set terminal pdf;";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".pdf';";
				// save direction
				direction = "images/" + iduser + ".pdf";
			} else {
				commandTerminal += "set terminal canvas jsdir './js/'  mousing;";
				commandTerminal += "set output '"
						+ context.getRealPath("plots") + "/" + iduser
						+ ".html';";
				// save direction
				direction = "plots/" + iduser + ".html";
			}
			// common config
			commandTerminal += "set autoscale;";
			commandTerminal += "set title '" + title + "';";
			commandTerminal += "set xlabel '" + labelxyz.get(0) + "';";
			commandTerminal += "set ylabel '" + labelxyz.get(1) + "';";

			// limits minx maxx miny maxy minz maxz
			if (limitxyz.size() == 6) {
				// limit min max for X
				if (limitxyz.get(0).isEmpty() && limitxyz.get(1).isEmpty()) {
					commandTerminal += "set xrange [*:*];";
				} else {
					commandTerminal += "set xrange [" + limitxyz.get(0) + ":"
							+ limitxyz.get(1) + "];";
				}
				// limit min max for Y
				if (limitxyz.get(2).isEmpty() && limitxyz.get(3).isEmpty()) {
					commandTerminal += "set yrange [*:*];";
				} else {
					commandTerminal += "set yrange [" + limitxyz.get(2) + ":"
							+ limitxyz.get(3) + "];";
				}
			}

			// add points and lines style to plot
			String pointlineadding = " with ";
			if (lines && points)
				pointlineadding += " linesp lt 1 pt 3 ";
			if (lines && !points)
				pointlineadding += " lines  ";
			if (!lines && points)
				pointlineadding += " points ps 1 ";
			if (!lines && !points)
				pointlineadding += " dots ";

			// add plot files
			int cont = 0;
			commandTerminal += "plot ";
			for (UserFile f : files) {
				commandTerminal += " '" + context.getRealPath("files") + "/"
						+ f.getIdfile() + ".txt' title '" + f.getTitle() + "' "
						+ pointlineadding;
				if (cont < files.size() - 1)
					commandTerminal += ",";
				cont++;
			}
			commandTerminal += ";";

			// Load gnuplot
			Boolean state = GnuplotLoad.setCommand(commandTerminal, context);
			if (state)
				return direction;// send direction of file
		}
		return null;

	}

	// 3Dimension---------------------------------------------------------------------
	@Override
	public String CreateImage3D(Boolean points, Boolean lines,
			String fileFormat, String title, List<String> labelxyz,
			List<String> limitxyz, String iduser)
			throws IllegalArgumentException {

		System.err.println("Function: CreateImage3D");

		// servlet context to find path
		ServletContext context = this.getServletContext();

		// files configuration
		List<UserFile> files = this.getUserFilesPlot(iduser);

		// if exist selected files
		if (files.size() > 0) {
			String direction = "";

			// set command
			String commandTerminal = "";

			// set terminal format
			if (fileFormat.equals("png")) {
				commandTerminal += "set terminal pngcairo;";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".png';";
				// save direction
				direction = "images/" + iduser + ".png";
			} else if (fileFormat.equals("svg")) {
				commandTerminal += "set terminal svg;";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".svg';";
				// save direction
				direction = "images/" + iduser + ".svg";
			} else if (fileFormat.equals("eps")) {
				commandTerminal += "set terminal postscript eps enhanced color font 'Helvetica,10';";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".eps';";
				// save direction
				direction = "images/" + iduser + ".eps";
			} else if (fileFormat.equals("pdf")) {
				commandTerminal += "set terminal pdf;";
				commandTerminal += "set output '"
						+ context.getRealPath("images") + "/" + iduser
						+ ".pdf';";
				// save direction
				direction = "images/" + iduser + ".pdf";
			} else if (fileFormat.equals("html")) {
				commandTerminal += "set terminal canvas jsdir './js/'  mousing;";
				commandTerminal += "set output '"
						+ context.getRealPath("plots") + "/" + iduser
						+ ".html';";
				// save direction
				direction = "plots/" + iduser + ".html";
			}

			// title and labels
			commandTerminal += "set autoscale;";
			commandTerminal += "set title '" + title + "';";
			commandTerminal += "set xlabel '" + labelxyz.get(0) + "';";
			commandTerminal += "set ylabel '" + labelxyz.get(1) + "';";
			commandTerminal += "set zlabel '" + labelxyz.get(2) + "';";

			// limits minx maxx miny maxy minz maxz
			if (limitxyz.size() == 6) {
				// limit min max for X
				if (limitxyz.get(0).isEmpty() && limitxyz.get(1).isEmpty()) {
					commandTerminal += "set xrange [*:*];";
				} else {
					commandTerminal += "set xrange [" + limitxyz.get(0) + ":"
							+ limitxyz.get(1) + "];";
				}
				// limit min max for Y
				if (limitxyz.get(2).isEmpty() && limitxyz.get(3).isEmpty()) {
					commandTerminal += "set yrange [*:*];";
				} else {
					commandTerminal += "set yrange [" + limitxyz.get(2) + ":"
							+ limitxyz.get(3) + "];";
				}
				// limit min max for Z
				if (limitxyz.get(4).isEmpty() && limitxyz.get(5).isEmpty()) {
					commandTerminal += "set zrange [*:*];";
				} else {
					commandTerminal += "set zrange [" + limitxyz.get(4) + ":"
							+ limitxyz.get(5) + "];";
				}
			}

			// add points and lines style to plot
			String pointlineadding = " with ";
			if (lines && points)
				pointlineadding += " pm3d at t";
			if (lines && !points)
				pointlineadding += " pm3d at t";
			if (!lines && points)
				pointlineadding += " pm3d at t";
			if (!lines && !points)
				pointlineadding += " pm3d at t";

			// add plot files
			int cont = 0;
			commandTerminal += "splot ";
			for (UserFile f : files) {
				commandTerminal += " '" + context.getRealPath("files") + "/"
						+ f.getIdfile() + ".txt' title '" + f.getTitle() + "' "
						+ pointlineadding;
				if (cont < files.size() - 1)
					commandTerminal += ",";
				cont++;
			}

			// Load gnuplot
			Boolean state = GnuplotLoad.setCommand(commandTerminal, context);
			if (state)
				return direction;// send direction of file
		}
		return null;

	}

	/*
	 * CALCULATE-METRICS----------------------------------------------------------
	 */
	@Override
	public List<MetricResults> CalculateER(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric Error Ratio");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();

		try {
			

			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				ErrorRatio er = new ErrorRatio(userfiles,
						Integer.parseInt(userfiles.get(0).getDimension()));
				results = er.getResults();
			}

		} catch (Exception e) {
			System.err.print("\nerror: " + e.toString());
		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateSP(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric Spacing");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();
		try {
			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				Spacing sp = new Spacing(userfiles);
				results = sp.getResults();
			} else {
				System.err.print("\nerror: null files");
			}

		} catch (Exception e) {
			System.err.print("\nerror: " + e.toString());

		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateHA(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric HyperArea");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();
		try {
			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				HyperArea ha = new HyperArea(userfiles);
				results = ha.getResults();
			} else {
				System.err.print("\nerror: null files");
			}

		} catch (Exception e) {
			System.err.print("\nerror: " + e.toString());

		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateHR(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric Hyperarea Ratio");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();

		try {
			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				HyperAreaRatio er = new HyperAreaRatio(userfiles,
						Integer.parseInt(userfiles.get(0).getDimension()));
				results = er.getResults();
			}

		} catch (Exception e) {
			System.err.print("\nerror: " + e.toString());
		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateGD(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric Generational Distance");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();

		try {
			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				GenDistance gd = new GenDistance(userfiles,
						Integer.parseInt(userfiles.get(0).getDimension()));
				results = gd.getResults();
			} else {
				System.err.print("\nerror: null files");
			}

		} catch (Exception e) {
			System.err.print("\nImpl error: " + e.toString());
		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateGNVG(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric GNVG");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();
		try {
			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				GNVG gnvg = new GNVG(userfiles);
				results = gnvg.getResults();
			} else {
				System.err.print("\nerror: null files");
			}

		} catch (Exception e) {
			System.err.print("\nerror: " + e.toString());

		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateC(String iduser)
			throws IllegalArgumentException {

		System.err.print("\nMetric Coverage");

		// result object
		List<MetricResults> results = new ArrayList<MetricResults>();

		try {
			// create user files in directory files
			ServletContext context = this.getServletContext();
			String path = context.getRealPath("files");
			
			// search files from user in metric selected
			List<UserFile> userfiles = DataFile_crud.getFullFilesMetric(iduser,path);

			// control exist both objects
			if (!userfiles.isEmpty() && userfiles != null) {
				// start calculation
				GenDistance gd = new GenDistance(userfiles,
						Integer.parseInt(userfiles.get(0).getDimension()));
				results = gd.getResults();
			} else {
				System.err.print("\nerror: null files");
			}

		} catch (Exception e) {
			System.err.print("\nImpl error: " + e.toString());
		}
		System.err.print("\nretorna resultado");
		return results;
	}

	@Override
	public List<MetricResults> CalculateE(String iduser)
			throws IllegalArgumentException {

		return null;
	}

	/*
	 * EXPORT-RESULTS----------------------------------------------------------
	 */
	@Override
	public Boolean ExportResults(String iduser, List<MetricResults> Results) throws IllegalArgumentException {
		System.err.println("Function: ExportResults");

		// servlet context to find path
		ServletContext context = this.getServletContext();
		ExportDataResult EDR = new ExportDataResult(context, iduser, Results);
		return EDR.WriteFile();
	}


	

}// end class
