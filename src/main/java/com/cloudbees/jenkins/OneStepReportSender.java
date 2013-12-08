package com.cloudbees.jenkins;

public class OneStepReportSender {

    /**
     * Send project build report with by project name and email addresses
     */
    public static void send(String project_name, String[] emails) {
        Report new_report = new Report(project_name);
        ReportStorage storage = ReportStorage.getInstance();
        storage.add(new_report);
        storage.sendReportEmail(emails);
        storage.clear();
    }

}
