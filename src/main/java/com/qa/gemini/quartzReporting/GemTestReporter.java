package com.qa.gemini.quartzReporting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GemTestReporter {


	
	private static TestCase_Details testCase_Details;
	private static JsonObject stepJson = new JsonObject();
	private static JsonArray steps;
	private static Suits_Details suiteDetails;
	private static QuartzReporting reporting ;



	public static void startSuite(String projectName, String env) {
		String s_run_id = projectName+"_"+GemReportingUtility.getCurrentTimeInMilliSecond()+"_"+env.toUpperCase();
		suiteDetails = new Suits_Details(s_run_id, projectName, env) ;
		reporting = new QuartzReporting(suiteDetails);
	}

	public static void startTestCase(String testcaseName, String category, String productType, boolean ignore) {
		steps = new JsonArray();
		testCase_Details = new TestCase_Details(testcaseName, category, GemReportingUtility.getCurrentUserName(), productType, ignore);
	}


	public static void addTestStep(String stepTitle, String stepDescription , STATUS status) {
		addTestStep(stepTitle, stepDescription, status,new HashMap<String, String>());
	}
	public static void addTestStep(String stepTitle, String stepDescription , STATUS status, String screenShotPath) {
		Map<String, String> scrnshot = new HashMap<String, String>();
		scrnshot.put("ScreenShot", screenShotPath);
		addTestStep(stepTitle, stepDescription, status,scrnshot);
	}

	public static void addTestStep(String stepTitle, String stepDescription , STATUS status, Map<String, String> extraKeys) {
		JsonObject step = new JsonObject();
		step.addProperty("title", stepTitle);
		step.addProperty("description", stepDescription);
		step.addProperty("status", status.name());	
		if(extraKeys!=null) {
			Set<String> extraKeySet = extraKeys.keySet();
			for(String key : extraKeySet) {
				step.addProperty(key, extraKeys.get(key));
			}
		}
		steps.add(step);
	}





	public static void endTestCase() {
		testCase_Details.setStatus(steps);
		testCase_Details.endTestCase();
		suiteDetails.addTestCaseDetail(testCase_Details);


		String testCaseRunID = testCase_Details.getTc_run_id();

		JsonObject testCaseStep= new JsonObject();
		testCaseStep.add("steps", steps);
		testCaseStep.add("metaData", createTestCaseMetaData());
		stepJson.add(testCaseRunID, testCaseStep);
	}

	private static JsonArray createTestCaseMetaData() {
		JsonArray metaData =  new JsonArray();
		JsonObject testcaseName = new JsonObject();
		testcaseName.addProperty("TESTCASE NAME", testCase_Details.getName());
		testcaseName.addProperty("SERVICE PROJECT", "");
		JsonObject dateOfExecution = new JsonObject();
		dateOfExecution.addProperty("value", testCase_Details.getStart_time());
		dateOfExecution.addProperty("type", "date");
		testcaseName.add("DATE OF EXECUTION",dateOfExecution);
		metaData.add(testcaseName);

		JsonObject executionTimeDetail = new JsonObject();
		JsonObject startTimeDetail = new JsonObject();
		startTimeDetail.addProperty("value", testCase_Details.getStart_time());
		startTimeDetail.addProperty("type", "date");
		executionTimeDetail.add("EXECUTION STARTED ON", startTimeDetail);

		JsonObject endTimeDetail =  new JsonObject();
		endTimeDetail.addProperty("value", testCase_Details.getEnd_time());
		endTimeDetail.addProperty("type", "date");
		executionTimeDetail.add("EXECUTION ENDED ON", endTimeDetail);

		executionTimeDetail.addProperty("EXECUTION DURATION", testCase_Details.getEnd_time()-testCase_Details.getStart_time()+ " seconds");
		metaData.add(executionTimeDetail); 
		metaData.add(getStepStats());

		return metaData;
	}

	private static JsonObject getStepStats() {
		JsonObject stepStats = new JsonObject();
		stepStats.addProperty("TOTAL", steps.size());
		Map<String, Integer> statMap = new HashMap<String, Integer>();

		for (JsonElement step : steps) {
			String statusName = step.getAsJsonObject().get("status").getAsString();
			if(statMap.get(statusName)== null) {
				statMap.put(statusName, 1)	;
			}else {
				statMap.put(statusName, statMap.get(statusName)+1);
			}
		}
		for (String  statusKey : statMap.keySet()) {
			stepStats.addProperty(statusKey, statMap.get(statusKey));
		}

		return stepStats;

	}

	public static void endSuite() {
		suiteDetails.endSuite();
		createReport();

	}

	private static void createReport() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//String suiteDetail = gson.toJson(reporting, QuartzReporting.class);
		JsonElement suiteDetail = gson.toJsonTree(reporting);
		System.out.println(suiteDetail.toString());
		System.out.println(stepJson.toString());
		try {
			GemReportingUtility.createReport(suiteDetail.toString(), stepJson.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}