package test;

import java.io.IOException;

import com.qa.gemini.quartzReporting.GemTestReporter;
import com.qa.gemini.quartzReporting.STATUS;

public class GemReportingTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		
		//1
		  GemTestReporter.startSuite("GemTest", "Test");
		  
		  GemTestReporter.startTestCase("GemTestCase 1", "Test", "GemJavaProject",
		  false);
		  
		  GemTestReporter.addTestStep("Step1", "stepDescription1", STATUS.PASS);
		  GemTestReporter.addTestStep("Step2", "stepDescription2", STATUS.PASS);
		  GemTestReporter.addTestStep("Step3", "stepDescription3", STATUS.PASS);
		  GemTestReporter.addTestStep("Step4", "stepDescription4", STATUS.PASS);
		  GemTestReporter.addTestStep("Step5", "stepDescription5", STATUS.PASS);
		  
		  GemTestReporter.endTestCase();
		  
		  GemTestReporter.endSuite();
		 
	}

}
