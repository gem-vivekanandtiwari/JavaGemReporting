package test;

import java.io.IOException;
import java.util.Random;

import com.qa.gemini.quartzReporting.GemTestReporter;
import com.qa.gemini.quartzReporting.STATUS;

public class GemReportingTest2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		GemTestReporter.startSuite("GemTest", "Test");		
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread() {
				public void run() {
					
					try {
						
						GemTestReporter.startTestCase("GemTestCase " + new Random().nextInt(), "Test", "GemJavaProject",
								false);
						GemTestReporter.addTestStep("Step1", "stepDescription1", STATUS.PASS);
						Thread.sleep(new Random().nextInt(5000));
						GemTestReporter.addTestStep("Step2", "stepDescription2", STATUS.PASS);
						Thread.sleep(new Random().nextInt(5000));
						GemTestReporter.addTestStep("Step3", "stepDescription3", STATUS.PASS);
						Thread.sleep(new Random().nextInt(5000));
						GemTestReporter.addTestStep("Step4", "stepDescription4", STATUS.PASS);
						Thread.sleep(new Random().nextInt(5000));
						GemTestReporter.addTestStep("Step5", "stepDescription5", STATUS.PASS);
						Thread.sleep(new Random().nextInt(5000));
						GemTestReporter.endTestCase();
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					}
				
			};
			thread.start();
			
		}	
		
		Thread.sleep(15000);
		GemTestReporter.endSuite();
	}

}
