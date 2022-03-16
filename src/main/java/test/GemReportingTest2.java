package test;

import java.io.IOException;
import java.util.Random;

import com.qa.gemini.quartzReporting.GemTestReporter2;
import com.qa.gemini.quartzReporting.STATUS;

public class GemReportingTest2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		GemTestReporter2.startSuite("GemTest", "Test");		
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread() {
				public void run() {
					GemTestReporter2.startTestCase("GemTestCase " + new Random().nextInt(), "Test", "GemJavaProject",
							false);
					GemTestReporter2.addTestStep("Step1", "stepDescription1", STATUS.PASS);
					GemTestReporter2.addTestStep("Step2", "stepDescription2", STATUS.PASS);
					GemTestReporter2.addTestStep("Step3", "stepDescription3", STATUS.FAIL);
					GemTestReporter2.addTestStep("Step4", "stepDescription4", STATUS.PASS);
					GemTestReporter2.addTestStep("Step5", "stepDescription5", STATUS.PASS);
					GemTestReporter2.endTestCase();
					System.out.println(new Random().nextInt());
					}
				
			};
			thread.start();
			
		}	
		
		Thread.sleep(60000);
		GemTestReporter2.endSuite();
	}

}
