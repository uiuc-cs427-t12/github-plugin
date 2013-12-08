package com.cloudbees.jenkins;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ReportStorageTest {
    
    Report report1;
    Report report2;
    
    @Before
    public void setup() {
        report1 = new Report(true);
        report1.name = "report1";
        report1.user = "user1";
        report1.result = "finish1";
        report1.finish_time = "finish_time1";
        report1.time = "time1";
        report1.memory = "memory1";
        report1.workspace = "/workspace1";
        report2 = new Report(false);
        report2.name = "report2";
        report2.user = "user2";
        report2.result = "finish2";
        report2.finish_time = "finish_time2";
        report2.time = "time2";
        report2.memory = "memory2";
        report2.workspace = "/workspace2";
    }

    @Test
    public void testAddAndClearReport() {
        ReportStorage reports = ReportStorage.getInstance();
        reports.add(report1);
        reports.add(report2);
        assertEquals(reports.size(), 2);
        Report[] report_arr = reports.getAll();
        assertEquals(report_arr.length, 2);
        assertTrue(report_arr[0].equals(report1) || report_arr[1].equals(report1));
        assertTrue(report_arr[0].equals(report2) || report_arr[1].equals(report2));
        reports.clear();
        assertEquals(reports.size(), 0);
        Report report3 = new Report(true);
        report3.name = "report3";
        report3.user = "user3";
        report3.result = "finish3";
        report3.finish_time = "finish_time3";
        report3.time = "time3";
        report3.memory = "memory3";
        report3.workspace = "/workspace3";
        reports.add(report3);
        assertEquals(reports.size(), 1);
        report_arr = reports.getAll();
        assertEquals(report_arr[0], report3);
        reports.clear();
    }
    
    @Test
    public void testToHTML() throws IOException {
        ReportStorage reports = ReportStorage.getInstance();
        reports.add(report1);
        reports.add(report2);
        String html = reports.toHTML();
        StringBuilder str = new StringBuilder();
        @SuppressWarnings("resource")
        BufferedReader br = new BufferedReader(new FileReader("report_test.html"));
        String line = null;
        while ((line = br.readLine()) != null) {
            str.append(line + "\n");
        }
        assertEquals(str.toString(), html + "\n");
        reports.clear();
    }
    
    /*
    @Test
    public void testEmail() {
        ReportStorage reports = ReportStorage.getInstance();
        reports.add(report1);
        reports.add(report2);
        String[] address = {"YOU_EMAIL"}; //Please enter your email here before run this test
        reports.sendReportEmail(address);
        reports.clear();
    }
    */

}
