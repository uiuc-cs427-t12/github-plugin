package com.cloudbees.jenkins;

import java.io.FileNotFoundException;

public class Report {

    public boolean success;
    public String name;
    public String result;
    public String user;
    public String time;
    public String finish_time;
    public String memory;
    public String workspace;
    
    private static String REPORT_1 = "<li style='width:100%;position:relative;margin:10px 0px;" + 
            "padding-bottom:40px;border-bottom:1px solid #888888;'><h3 style='margin:0px 0px 20px 0px;" + 
            "font-size:16px;'>";
    private static String REPORT_2 = " (";
    private static String REPORT_3 = "): <span style='font-size:16px;font-weight:bold;color:";
    private static String REPORT_4 = ";'>";
    private static String REPORT_5 = "</span></h3><pre style='width:100%;display:block;font-size:12px;'>";
    private static String REPORT_6 = "</pre></li>";
    private String na = "N/A";
    
    /**
     * Make new report object by project name
     * @param project_name : name of project to pull report
     */
    public Report(String project_name) {
        this.name = project_name;
        try {
            this.success = buildReportParser.getFinished(project_name).equalsIgnoreCase("SUCCESS");
            String user = buildReportParser.getUser(project_name);
            this.user = user.length() == 0 ? na : user;
            String result = buildReportParser.getResults(project_name);
            this.result = result.length() == 0 ? na : result;
            String finish_time = buildReportParser.getFinishTime(project_name);
            this.finish_time = finish_time.length() == 0 ? na : finish_time;
            String time = buildReportParser.getTotalTime(project_name);
            this.time = time.length() == 0 ? na : time;
            String memory = buildReportParser.getFinalMemory(project_name);
            this.memory = memory.length() == 0 ? na : memory;
            String workspace = buildReportParser.getWorkSpace(project_name);
            this.workspace = workspace.length() == 0 ? na : workspace;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Make new report object
     * @param success : success or fail
     * @param name : name of reort
     * @param log : build log
     * @param link : link to github
     * @param version : commit version
     */
    public Report(boolean success) {
        this.success = success;
    }
    
    /**
     * output html formatted report
     * @param idx : index of report
     * @return html formatted report as a String
     */
    public String toHTML(int idx) {
        StringBuilder str = new StringBuilder(REPORT_1);
        str.append(Integer.toString(idx));
        str.append(". ");
        str.append(this.name);
        str.append(REPORT_2);
        str.append(this.workspace);
        str.append(REPORT_3);
        if(this.success) {
            str.append("#00ff00");
            str.append(REPORT_4);
            str.append("Success");
        } else {
            str.append("#ff0000");
            str.append(REPORT_4);
            str.append("Fail");
        }
        str.append(REPORT_5);
        str.append("User: " + this.user + "\n");
        str.append("Result: " + this.result + "\n");
        str.append("Finished Time: " + this.finish_time + "\n");
        str.append("Total Time: " + this.time + "\n");
        str.append("Total Memory: " + this.memory + "\n");
        str.append(REPORT_6);
        return str.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.user == null) ? (other.user != null) : !this.user.equals(other.user)) {
            return false;
        }
        if ((this.time == null) ? (other.time != null) : !this.time.equals(other.time)) {
            return false;
        }
        if ((this.memory == null) ? (other.memory != null) : !this.memory.equals(other.memory)) {
            return false;
        }
        if ((this.finish_time == null) ? (other.finish_time != null) : !this.finish_time.equals(other.finish_time)) {
            return false;
        }
        if ((this.result == null) ? (other.result != null) : !this.result.equals(other.result)) {
            return false;
        }
        if ((this.workspace == null) ? (other.workspace != null) : !this.workspace.equals(other.workspace)) {
            return false;
        }
        return this.success == other.success;       
    }

}
