package com.cloudbees.jenkins;;
 
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
public class testBuildReportParser {
 
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //create temp jenkins source folder, and temp log file in that folder
        //create a job folder
        String home = System.getProperty("user.home");
        File testJob=new File(home+File.separator+".jenkins"+File.separator,"jobs");
        testJob.mkdir();
        //create a job whose name is testjob. (it's actually a folder)
        File  testJobName=new File(testJob.getAbsolutePath(),"testjob");
        testJobName.mkdir();
        //create a nextBuildNumber file in the testjob folder
        File nextBuildNumber=new File(testJobName.getAbsolutePath(),"nextBuildNumber");
        String content="2";
        BufferedWriter out;
        out= new BufferedWriter(new FileWriter(nextBuildNumber));
            out.write(content, 0, content.length());
         
        out.close();
        //create a build folder in testjob folder
        File builds=new File(testJobName.getAbsolutePath(),"builds");
        builds.mkdir();
        // create 1 folder in this folder
        File one=new File(builds.getAbsolutePath(),"1");
        one.mkdir();
        //create an initial log file in 1 folder
        File log=new File(one.getAbsolutePath(),"log");
        copy("log_success",log,log.getAbsolutePath());
          
        //create 2 folder in this folder
        File two=new File(builds.getAbsolutePath(),"2");
        two.mkdir();
        File log2=new File(two.getAbsolutePath(),"log");
        copy("log_failture",log2,log2.getAbsolutePath());
          
        //System.out.println(home+File.separator+"Desktop");
         
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
     
    private static void copy(String log, File result, String destPath) throws IOException {
        File dir = new File("");
        System.out.println("Path: "+dir.getAbsolutePath());
        //System.out.println(dir.getp)
        String source = dir.getAbsolutePath()+File.separator+log;
   
        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
   
        BufferedWriter out = new BufferedWriter(new FileWriter(result));
   
        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            //Process each line and add output to Dest.txt file
            out.write(aLine);
            out.newLine();
        }
   
        // do not forget to close the buffer reader
        in.close();
   
        // close buffer writer
        out.close();
    }
    private static String expectedResult() throws IOException {
         File dir = new File("");
         System.out.println("lala:"+dir.getCanonicalPath());
         System.out.println("Path: "+dir.getAbsolutePath());
         String source = dir.getAbsolutePath()+File.separator+"log_success";
    
         File fin = new File(source);
         FileInputStream fis = new FileInputStream(fin);
         BufferedReader in = new BufferedReader(new InputStreamReader(fis));
    
    
         String aLine = null;
         String output="";
         while ((aLine = in.readLine()) != null) {
             //Process each line and add output to Dest.txt file
             output=output+aLine+"\n";
         }
    
         // do not forget to close the buffer reader
         in.close();
   
         return output;
    }
}
    
