package com.cloudbees.jenkins;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Parse the log file for the given builds
 * @author Rishi Girish
 *
 */

public class buildReportParser {
	
	private static final Exception NullExceptions = null;
	
	private static String filename;
	private static File file;
	private static String report;
	private	static Vector<String> unedited_report;	
	private static int last_build_num;
	
	/*
	 * Intialiser for the parse. 
	 * @argument: String in -> the name of the project 
	 */
	private static void initializer(String in) throws FileNotFoundException
	{
		if(in == null)
			try {
				throw NullExceptions;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		buildReportParser.filename = System.getProperty("user.home") + File.separator + ".jenkins" + File.separator + "jobs" + File.separator + in + File.separator + "nextBuildNumber";
		buildReportParser.file = new File(buildReportParser.filename);
		Scanner input = new Scanner(buildReportParser.file);
		buildReportParser.unedited_report = new Vector<String>();

		buildReportParser.last_build_num = input.nextInt() - 1;
		
		buildReportParser.filename = System.getProperty("user.home") + File.separator + ".jenkins" + File.separator + "jobs" + File.separator + in + File.separator + "builds" + File.separator + last_build_num + File.separator + "log";
		buildReportParser.file = new File(buildReportParser.filename);
		
		input.close();
		
		input = new Scanner(buildReportParser.file);
		buildReportParser.report = "";
			
		while(input.hasNext())
		{
			String line = input.nextLine();
			buildReportParser.report = buildReportParser.report + line + "=end=";
			buildReportParser.unedited_report.addElement(line);		
		}
	
		input.close();

		return;
	}	

	/*
	 * returns: String -> the name of the user to initiated the build.
	 */
	public static String getUser(String project_name) throws FileNotFoundException
	{	
		buildReportParser.initializer(project_name);
					
		int idx = buildReportParser.report.indexOf("=end=", 0);
		int start = buildReportParser.report.indexOf("Started by", 0);
		
		int index = buildReportParser.report.indexOf("anonymous", 0);
		
		if(index > start)
			return buildReportParser.report.substring(index, idx);
		else
			return buildReportParser.report.substring(start + "Started by".length(), idx);
	}
	
	public static String getWorkSpace(String project_name) throws FileNotFoundException 
	{
		buildReportParser.initializer(project_name);
		
		int idx = buildReportParser.report.indexOf("Building in workspace");
		int end = buildReportParser.report.indexOf("=end", idx);
		
		return buildReportParser.report.substring(idx + "Building in workspace: ".length(), end);
	}
	
	public static String getFinished(String project_name) throws FileNotFoundException
	{
		buildReportParser.initializer(project_name);
		
		int idx = buildReportParser.report.indexOf("Finished:");
		int end = buildReportParser.report.indexOf("=end", idx);
		
		return buildReportParser.report.substring(idx + "Finished: ".length(), end);
	}
	
	public static String getResults(String project_name) throws FileNotFoundException
	{
		buildReportParser.initializer(project_name);
		
		int idx = buildReportParser.report.indexOf("Results :");
		
		if(idx < 0)
			return "";
		
		int runs = buildReportParser.report.indexOf("Tests run:", idx);
		int end = buildReportParser.report.indexOf("=end=", runs);
		
		return buildReportParser.report.substring(runs, end);
	}

	public static String getcompleteReport(String project_name) throws FileNotFoundException
	{
		buildReportParser.initializer(project_name);
		
		String [] temp = buildReportParser.unedited_report.toArray(new String[0]);
		String retval = "";

		for(int i = 0; i < temp.length; i++)
			retval = retval + temp[i] + System.lineSeparator();
		
		return retval; 
	}
	
	public static String getTotalTime(String project_name) throws FileNotFoundException
	{
		buildReportParser.initializer(project_name);

		int idx = buildReportParser.report.indexOf("Total time: ");
		
		if(idx < 0)	
			return "";
		
		int end = buildReportParser.report.indexOf("=end=", idx);
		return buildReportParser.report.substring(idx + "Total time:".length(), end);
	}

	public static String getFinishTime(String project_name) throws FileNotFoundException
	{
		buildReportParser.initializer(project_name);
		
		int idx = buildReportParser.report.indexOf("Finished at: ");

		if(idx < 0)
			return "";
		
		int end = buildReportParser.report.indexOf("=end=", idx);
		return buildReportParser.report.substring(idx + "Finished at:".length(), end);
	}

	public static String getFinalMemory(String project_name) throws FileNotFoundException
	{
		buildReportParser.initializer(project_name);
		
		int idx = buildReportParser.report.indexOf("Final Memory: ");
		
		if(idx < 0)
			return "";

		int end = buildReportParser.report.indexOf("=end", idx);
		return buildReportParser.report.substring(idx + "Final Memory:".length(), end);
	}
}
