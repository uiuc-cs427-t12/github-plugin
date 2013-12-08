package com.cloudbees.jenkins;

import java.util.Vector;

/**
 * Used to store reports and output rendered html code
 * @author Yinshen Tang
 *
 */
public class ReportStorage {
    
    private static volatile ReportStorage instance = null;
    
    private Vector<Report> reports;
    
    //Strings used as html template
    private static String FRAME_1 = "<div style='width:100%;position:relative;border:3px solid #3498db;" + 
            "box-sizing:border-box;'><div style='width:96%;position:relative;margin:2%;'>" + 
            "<h1 style='margin:0px auto 20px auto;font-size:20px;'>Jenkins Build Report</h1>" + 
            "<h2 style='margin:0px auto 20px auto;font-size:18px;'><span style='font-size:18px;" + 
            "font-weight:bold;color:";
    private static String FRAME_2 = ";'>";
    private static String FRAME_3 = "</span> build success.</h2></div><ul style='" + 
            "width:96%;position:relative;list-style:none;margin:2%;padding:0px;'>";
    private static String FRAME_4 = "</ul></div>";
    
    private ReportStorage() {
        reports = new Vector<Report>(5);
    }
    
    /**
     * get instance function for Singleton Pattern
     * @return : the only instance of ReportStorage in the System
     */
    public static ReportStorage getInstance() {
        if(instance == null) {
            synchronized(ReportStorage.class) {
                if(instance == null) {
                    instance = new ReportStorage();
                }
            }
        }
        return instance;
    }
    
    /**
     * add new report
     * @param report : report object to add
     */
    public void add(Report report) {
        reports.add(report);
    }
    
    /**
     * get the number of reports stored
     * @return : number of reports
     */
    public int size() {
        return reports.size();
    }
    
    /**
     * get all report objects as an array
     * @return : array of report object
     */
    public Report[] getAll() {
        return reports.toArray(new Report[0]);
    }
    
    /**
     * clear the report storage
     */
    public void clear() {
        reports.clear();
    }
    
    /**
     * count the number of success build project
     * @return : number of success build project
     */
    private int countSuccessBuild() {
        int success_count = 0;
        for(Report report : reports) {
            if(report.success) {
                success_count++;
            }
        }
        return success_count;
    }

    /**
     * output html formatted report
     * @return : html formmated reort as String
     */
    public String toHTML() {
        if(reports.size() == 0) {
            return null;
        }
        StringBuilder str = new StringBuilder(FRAME_1);
        int success_count = countSuccessBuild();
        if(success_count < reports.size()) {
            str.append("#ff0000");
        } else {
            str.append("#00ff00");
        }
        str.append(FRAME_2);
        str.append(Integer.toString(success_count));
        str.append("/");
        str.append(Integer.toString(reports.size()));
        str.append(FRAME_3);
        for(int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);
            str.append(report.toHTML(i + 1));
        }
        str.append(FRAME_4);
        return str.toString();
    }
    
    /**
     * Send reports thru email
     */
    public void sendReportEmail(String[] address) {
        Email.sendHtmlEmail(address, "Jenkins Build Report", this.toHTML());
    }

}
