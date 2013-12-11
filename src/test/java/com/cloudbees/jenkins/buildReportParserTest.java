package com.cloudbees.jenkins;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cloudbees.jenkins.buildReportParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @author Pengyu Ren, Kiwook Lee
 *
 */
public class buildReportParserTest {
    
    
    /**
     *this method basically create a testjob in jenkins source folder so that it can be used in the following test cases.
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
       
        String home = System.getProperty("user.home");
        File testJob=new File(home+File.separator+".jenkins"+File.separator,"jobs");
        testJob.mkdir();
        
        File testJobName=new File(testJob.getAbsolutePath(),"testjob");
        testJobName.mkdir();
        
        File nextBuildNumber=new File(testJobName.getAbsolutePath(),"nextBuildNumber");
        String content="2";
        BufferedWriter out;
        out= new BufferedWriter(new FileWriter(nextBuildNumber));
        out.write(content, 0, content.length());
        
        out.close();
        
        File builds=new File(testJobName.getAbsolutePath(),"builds");
        builds.mkdir();
        
        File one=new File(builds.getAbsolutePath(),"1");
        one.mkdir();
        
        File log=new File(one.getAbsolutePath(),"log");
        copy("log_success",log,log.getAbsolutePath());
        
        
        File two=new File(builds.getAbsolutePath(),"2");
        two.mkdir();
        File log2=new File(two.getAbsolutePath(),"log");
        copy("log_failture",log2,log2.getAbsolutePath());
        
        
        
    }
    
    @Test
    public void testGetUser() throws FileNotFoundException {
        String result=buildReportParser.getUser("testjob");
        
        assertEquals(result,"anonymous");
    }
    
    @Test
    public void testGetWorkSpace() throws FileNotFoundException {
        String result=buildReportParser.getWorkSpace("testjob");
        //System.out.println(result);
        assertEquals(result,"home/t12/.jenkins/jobs/gitTest_T12_Seungchul/workspace");
    }
    @Test
    public void testGetFinished() throws FileNotFoundException {
        String result=buildReportParser.getFinished("testjob");
        //System.out.println(result);
        assertEquals(result,"SUCCESS");
    }
    @Test
    public void testGetResults() throws FileNotFoundException {
        String result=buildReportParser.getResults("testjob");
        //System.out.println(result);
        assertEquals(result,"Tests run: 32, Failures: 0, Errors: 0, Skipped: 0");
    }
    @Test
    public void testGetCompleteReports() throws IOException {
        String result=buildReportParser.getcompleteReport("testjob");
        //System.out.println(result);
        
        assertEquals(result,expectedResult());
    }
    @Test
    public void testGetTotalTime() throws FileNotFoundException {
        String result=buildReportParser.getTotalTime("testjob");
        //System.out.println(result);
        assertEquals(result," 1:01.943s");
    }
    @Test
    public void testGetFinishedTime() throws FileNotFoundException {
        String result=buildReportParser.getFinishTime("testjob");
        //System.out.println(result);
        assertEquals(result," Sat Nov 16 20:20:33 CST 2013");
    }
    @Test
    public void testGetFinalmemory() throws FileNotFoundException {
        String result=buildReportParser.getFinalMemory("testjob");
        //System.out.println(result);
        assertEquals(result," 38M/355M");
    }
    /**
     *This method copy the log file, which is log-success and log-failture, into jenkins sourcefolder.
     */
    private static void copy(String log, File result, String destPath) throws IOException {
        File dir = new File("");
        System.out.println("Path: "+dir.getAbsolutePath());
        
        String source = dir.getAbsolutePath()+inProjectPath()+File.separator+log;
        
        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        
        BufferedWriter out = new BufferedWriter(new FileWriter(result));
        
        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            
            out.write(aLine);
            out.newLine();
        }

        in.close();
        out.close();
    }
    /**
     *This helper function is going to used in testGetCompleteReports().
      It's going to test if the complete reports equals to what we expected.
     */
    private static String expectedResult() throws IOException {
        File dir = new File("");
        
        String source = dir.getAbsolutePath()+inProjectPath()+File.separator+"log_success";
        
        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        
        
        String aLine = null;
        String output="";
        while ((aLine = in.readLine()) != null) {
            output=output+aLine+"\n";
        }
        
        in.close();
        
        return output;
    }
    /**
     *this method will help to locate the correct path of the log_success and log_failture files. 
     */
    private static String inProjectPath() {
        String inProjectPath = File.separator+"src"+File.separator+"test"+File.separator+File.separator+"java"+File.separator+"com"+File.separator+"cloudbees"+File.separator+"jenkins";
        return inProjectPath;
    }
    

}
    
