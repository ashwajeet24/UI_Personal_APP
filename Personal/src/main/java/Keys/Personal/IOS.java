package Keys.Personal;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import xmlwise.Plist;
import xmlwise.XmlParseException;

public class IOS {
	public static String Branch_Key_from_cloud;
	public static Object Branch_Key_from_code;
	public static String Flurry_Key_from_cloud;
	public static Object Flurry_Key_from_Code;
	public static Object Gimbal_Key_from_code;
	public static String Gimbal_key_from_cloud;
	public static Object Pushwoosh_key_from_code;
	public static String pushwoosh_key_from_cloud;
	static String organization_id ;
	static String ID;
	public static String Token;
	static Response Response1;
	static String  Response2;
	static JSONObject jsn;
	static JSONArray array;
	static JSONObject ja;
	public static String key;
	public static String flurry_key;
	public static String gimbal_key;
	public static String key_Demo_flurry;
	public static String GA_Token;
	static Response GA_Response1;
	static String  GA_Response2;
	static JSONObject GA_jsn;
	static JSONArray GA_array;
	static JSONObject GA_ja;
	public static String GA_key;
	public static Object GA_Key_from_code;
	public static String GA_Key_from_cloud;
	public static String GA_Key;
	static String Key1;
	static String finalprodkey;
	static JSONTokener tokener;

	static File file;
	static FileWriter writer;
	static Object nextKey;
	static String Prod;
	
	public static Object Appname;
	public static String appname;
	public static String appfilename;
	static ExtentReports extent;
	static ExtentTest logger;
	


	public static void main(String[] args)throws JSONException, IOException, ParserConfigurationException, SAXException, XPathExpressionException, XmlParseException  {
		extent = new ExtentReports ("C:/Results/Report.html", true);
		//extent.addSystemInfo("Environment","Environment Name")
		extent
                .addSystemInfo("Host Name", "Hopscotch")
                .addSystemInfo("Environment", "QA Production_IOS")
                .addSystemInfo("User Name", "QA");
                //loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
                //You could find the xml file below. Create xml file in your project and copy past the code mentioned below
                //extent.loadConfig(new File("/Users/ashwagajre/Desktop/script/extent-config.xml"));
		SoftAssert softAssertion= new SoftAssert();
		

		
		
	        
		 Map<String, Object> properties_appname = Plist.load("C:/Results/Info.plist");
		
			for (Map.Entry<String, Object> entry : properties_appname.entrySet()) {
				if(entry.getKey().contains("CFBundleDisplayName")) {
				Key1 =entry.getKey(); 
				Appname = entry.getValue();
					}
				}
			System.out.println("App Name is :"+ Appname);
		
		
			logger =extent.startTest(Appname +"- IOS KeyAutomation");
			appname = Appname.toString();
			Map<String, Object> properties = Plist.load("C:/Results/Info.plist");
		 //System.out.println(properties);
			for (Map.Entry<String, Object> entry : properties.entrySet()) {
				if(entry.getKey().contains("branch_key")) {
				Key1 =entry.getKey(); 
				nextKey = entry.getValue();
					}
				}
			//System.out.println(nextKey);
			
			Prod = Plist.toPlist(nextKey);
			file = new File("C:/Results/app1.plist");
			writer=new FileWriter(file);
			writer.write(Prod);
			writer.close();
				
			Map<String, Object> prod = Plist.load("C:/Results/app1.plist");
			
			for (Map.Entry<String, Object> entry1 : prod.entrySet()) {
			if(entry1.getKey().contains("live")) {
	
			Branch_Key_from_code = entry1.getValue();
			}
			}
			System.out.println("Branch Key From_code : "+Branch_Key_from_code);
		
	try {
		
		BufferedReader Branch_br = new BufferedReader(new FileReader("C:/Results/data_branch.json"));
		tokener = new JSONTokener(Branch_br);
		JSONArray Branch_array = new JSONArray(tokener);
	 
		for(int i=0;i<Branch_array.length();i++) 
	{
		JSONObject ja = Branch_array.getJSONObject(i);
		if(ja.getString("name").equals(appname)) 
		{
		Branch_Key_from_cloud = ja.getString("branch_key");
		}
	}
		System.out.println(Branch_Key_from_cloud);
	}catch(Throwable t) {
		t.getStackTrace();
	}
	
		if(Branch_Key_from_code.equals( Branch_Key_from_cloud)) {
		System.out.println("Test case passed: Key Matches");
		logger.log(LogStatus.INFO, "Branch Code Key: "+ Branch_Key_from_code);
		logger.log(LogStatus.INFO, "Branch Cloud Key: "+ Branch_Key_from_cloud);
		logger.log(LogStatus.PASS, " <font color = Green> Branch Key From Code and Branch Key From Cloud are a MATCH ");
		//System.out.println(appfilename);
	}else {
		logger.log(LogStatus.INFO, "Branch Code Key: "+ Branch_Key_from_code);
		logger.log(LogStatus.INFO, "Branch Code Key: "+ Branch_Key_from_cloud);
		logger.log(LogStatus.FAIL, " <font color = Red> Branch Key From Code and Branch Key From Cloud do not MATCH ");
		softAssertion.assertAll();
	}
		
		//scan.close();
		Map<String, Object> properties1 = Plist.load("C:/Results/appConfig.plist");
		for (Map.Entry<String, Object> entry : properties1.entrySet()) {
			if(entry.getKey().contains("apiKeys")) {
				Key1 =entry.getKey(); 
				nextKey = entry.getValue();
			}
		}

			Prod = Plist.toPlist(nextKey);
			file = new File("C:/Results/app1.plist");
			writer=new FileWriter(file);
			writer.write(Prod);
			writer.close();
			
			Map<String, Object> prod1 = Plist.load("C:/Results/app1.plist");
		
			for (Map.Entry<String, Object> entry1 : prod1.entrySet()) {
			if(entry1.getKey().contains("prod")) {

			nextKey = entry1.getValue();
				}
		}
		

			finalprodkey = Plist.toPlist(nextKey);
	
		 file = new File("C:/Results/app1.plist");
			writer=new FileWriter(file);
			writer.write(finalprodkey);
			writer.close();	
		
			Map<String, Object> finalkey = Plist.load("C:/Results/app1.plist");

			for (Map.Entry<String, Object> entry : finalkey.entrySet()) {
			if(entry.getKey().contains("FlurryAnalytics")) {	
				Flurry_Key_from_Code = entry.getValue();
		}
}

			System.out.println(Flurry_Key_from_Code);
		

  try {
	       
     Client client=Client.create(); 
		  WebResource webResource = client.resource("https://api-metrics.flurry.com/public/v1/data/appUsage/all/app;show=all?metrics=sessions,&dateTime=2015-01-01/2019-01-01");
		  MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

		  String Token = "Bearer " +" eyJhbGciOiJIUzI1NiIsImtpZCI6ImZsdXJyeS56dXVsLnByb2Qua2V5c3RvcmUua2V5LjIifQ.eyJpc3MiOiJodHRwczovL3p1dWwuZmx1cnJ5LmNvbTo0NDMvdG9rZW4iLCJpYXQiOjE1MTgxNDM3MDAsImV4cCI6MzMwNzUwNTI1MDAsInN1YiI6IjQxNDY5NSIsImF1ZCI6IjQiLCJ0eXBlIjo0LCJqdGkiOiIzMzQ4In0.i_skZy93Kzfu1sJNGjsfjr2qR1ryD8g0S5lcNMnj3Hk"; 
		  ClientResponse response = null;
		  response = webResource.queryParams(queryParams)
				  .header("Content-Type", "application/json;charset=UTF-8")
				  .header("Authorization", Token)
				  .get(ClientResponse.class);

		  String jsonStr = response.getEntity(String.class);
		 // System.out.println(jsonStr);
		  JSONObject jsn=new JSONObject(jsonStr);
		  JSONArray Flurry_array=jsn.getJSONArray("rows");
		
		  for(int i=0;i<Flurry_array.length();i++)
		  {
			  JSONObject ja=Flurry_array.getJSONObject(i);
			  if(ja.getString("app|name").equals(appname)&&(ja.getString("app|platform|name")).equals("iPhone")) {
				  System.out.println("The API key from the cloud are:"+ja.getString("app|apiKey"));
				  Flurry_Key_from_cloud = ja.getString("app|apiKey");
				 // System.out.println(Flurry_Key_from_cloud);
				  //softAssertion.assertEquals(Flurry_Key_from_Code, Flurry_Key_from_cloud);
			  }
		  }
		  
  }catch(Throwable t) {
	  t.getStackTrace();
  }
				  if (Flurry_Key_from_Code.equals(Flurry_Key_from_cloud)) {
					  	logger.log(LogStatus.INFO, "Flurry Code Key: "+ Flurry_Key_from_Code);
						logger.log(LogStatus.INFO, "Flurry Cloud Key: "+ Flurry_Key_from_cloud);
						logger.log(LogStatus.PASS, "<font color = Green> Flurry Code Key and Flurry Cloud Key are a MATCH ");
				  //System.out.println(appfilename);
			  }
 
		  else{
				logger.log(LogStatus.INFO, "Flurry Code Key: "+ Flurry_Key_from_Code);
				logger.log(LogStatus.INFO, "Flurry Cloud Key: "+ Flurry_Key_from_cloud);
				logger.log(LogStatus.PASS, "<font color = Red> Flurry Code Key and Flurry Cloud Key do not MATCH ");
		  }  	//softAssertion.assertAll();
		 

					 Map<String, Object> properties2 = Plist.load("C:/Results/Info.plist");
					 //System.out.println(properties1);
						for (Map.Entry<String, Object> entry : properties2.entrySet()) {
							if(entry.getKey().contains("Pushwoosh_APPID")) {
							Key1 =entry.getKey(); 
							Pushwoosh_key_from_code= entry.getValue();
								}
							}
						System.out.println("PushWoosh key From Code: "+ Pushwoosh_key_from_code);
		

			 

try {

					RestAssured.baseURI ="https://cp.pushwoosh.com";
					
					String res = given().
					body("{\"request\":{\"auth\":\"Xf9HnkaUbZCqO2G6ogWzbH00T3mBoBiw9X3QaxjQNzFBxoITIpPry0ra3B0AWkBTaUgt7HKP2EBdsR49c4uC\"}}").
					when().
					post("/json/1.3/getApplications").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().asString();
					
					
					//System.out.println(res);
					//System.out.println("Enter the app Name Like Auburn Athletics");
					//Scanner scan = new Scanner(System.in);
					//String appfilename = scan.nextLine();
					
					
					JsonPath js = new JsonPath(res);
					Map<String, String> applicationsMap = js.getMap("response.applications");
					
					Set<String> applicationKeys = applicationsMap.keySet();
				
					for( String key:applicationKeys){
						if(applicationsMap.get(key).contentEquals(appname)) {
							//System.out.println("AppName: " + applicationsMap.get(key) + ", Key value: " + key);
							//logger.log(LogStatus.PASS, "The Appname in cloud is: "+applicationsMap.get(key) );
							//logger.log(LogStatus.PASS, "The Key in the cloud is: "+key );
							pushwoosh_key_from_cloud=key;
							//System.out.println(pushwoosh_key_from_cloud);
						}
						
					}
							if(Pushwoosh_key_from_code.equals(pushwoosh_key_from_cloud)){
							//System.out.println("Key from Code: "+ Pushwoosh_key_from_code + "Key from Pushwoosh"+ pushwoosh_key_from_cloud);
							logger.log(LogStatus.INFO, "PushWoosh Code Key: "+ Pushwoosh_key_from_code);
							logger.log(LogStatus.INFO, "PushWoosh Cloud Key: "+ pushwoosh_key_from_cloud);
							logger.log(LogStatus.PASS, " <font color = Green> PushWoosh Code Key and PushWoosh Cloud Key are a MATCH ");
						}else {
							logger.log(LogStatus.INFO, "PushWoosh Code Key: "+ Pushwoosh_key_from_code);
							logger.log(LogStatus.INFO, "PushWoosh Cloud Key: "+ pushwoosh_key_from_cloud);
							logger.log(LogStatus.FAIL, " <font color = Red> PushWoosh Code Key and PushWoosh Cloud Key do not MATCH ");
				}
}catch (Throwable t){
	t.printStackTrace();
	
}
							
		Map<String, Object> properties3 = Plist.load("C:/Results/appConfig.plist");
		for (Map.Entry<String, Object> entry : properties3.entrySet()) {
			if(entry.getKey().contains("apiKeys")) {
				Key1 =entry.getKey(); 
				nextKey = entry.getValue();
				}
			}
	
			Prod = Plist.toPlist(nextKey);
			file = new File("C:/Results/app1.plist");
			writer=new FileWriter(file);
			writer.write(Prod);
			writer.close();		
							
			Map<String, Object> prod3 = Plist.load("C:/Results/app1.plist");
			for (Map.Entry<String, Object> entry1 : prod3.entrySet()) {
				if(entry1.getKey().contains("prod")) {
					nextKey = entry1.getValue();
				}
			}


			finalprodkey = Plist.toPlist(nextKey);
			file = new File("C:/Results/app1.plist");
			writer=new FileWriter(file);
			writer.write(finalprodkey);
			writer.close();	

	Map<String, Object> finalkey1 = Plist.load("C:/Results/app1.plist");
	
	for (Map.Entry<String, Object> entry : finalkey1.entrySet()) {
	if(entry.getKey().contains("GimbalKey")) {	
	
	 Gimbal_Key_from_code= entry.getValue();
}
}

	System.out.println(Gimbal_Key_from_code);
						 
						//System.out.println(Gimbal_Key_from_code);
							
						         
		try {
			
		
						 	BufferedReader Gimbal_id_br = new BufferedReader(new FileReader("C:/Results/Gimbalconvertjson.json"));
						    	tokener = new JSONTokener(Gimbal_id_br);
						    	JSONArray Gimbal_arr = new JSONArray(tokener);
						    	for(int i=0;i<Gimbal_arr.length();i++) 
						    {
						    	JSONObject jsonobj = Gimbal_arr.getJSONObject(i);
						    	if(jsonobj.getString("Name").equals(appname)) 
						    	{
						    	String Api_token = jsonobj.getString("Server API Key");
						    	System.out.println(Api_token);
						    	String s1 =(jsonobj.getString("Server API Key"));
						        org.jsoup.nodes.Document doc1=Jsoup.parse(s1);
						        org.jsoup.nodes.Element e1=doc1.select("input").first();
						        organization_id=e1.attr("value");
						    	
						    	}
						    }
						         
						         		    	
						         
						 		RestAssured.baseURI ="https://manager.gimbal.com";
								
								String First_Gimbal_response =given().header("AUTHORIZATION","Token "+ organization_id).
								when().
								get("/api/v2/applications").
								then().contentType("application/json").extract().asString();
								System.out.println(First_Gimbal_response);
								
								JSONArray array=new JSONArray(First_Gimbal_response);
								try {
									  for(int i=0;i<array.length();i++)
									  {
										  JSONObject ja=array.getJSONObject(i);
										  if(ja.toString().contains("ios")) {
											  ID = ja.getString("id");
										  }
									  }
								}catch(Exception e) {
									System.out.println("Array issue");
								}

								String nextRes =given().header("AUTHORIZATION", "Token " + organization_id).
								when().
								get("/api/v2/applications/"+ID).
								then().contentType("application/json").extract().asString();
								System.out.println(nextRes);
								JsonPath nextjs = new JsonPath(nextRes);
								 Gimbal_key_from_cloud = nextjs.get("api_key");
								 
								 
		}catch(Throwable t){
			t.getStackTrace();
		}
								//System.out.println(Gimbal_key_from_cloud);
									if(Gimbal_Key_from_code.equals (Gimbal_key_from_cloud)){
									//System.out.println("Test case passed: Key Matches");
										logger.log(LogStatus.INFO, "Gimbal Code Key: "+ Gimbal_Key_from_code);
										logger.log(LogStatus.INFO, "Gimbal Cloud Key: "+ Gimbal_key_from_cloud);
										logger.log(LogStatus.PASS, "<font color = Green> Gimbal Code Key and Gimbal Cloud Key are a MATCH ");
								}else{
									logger.log(LogStatus.INFO, "Gimbal Code Key: "+ Gimbal_Key_from_code);
									logger.log(LogStatus.INFO, "Gimbal Cloud Key: "+ Gimbal_key_from_cloud);
									logger.log(LogStatus.FAIL, "<font color = Red> Gimbal Code Key and Gimbal Cloud Key do not MATCH ");
								}
							
							
									
									File file_GAKEY = new File("C:/Results/Google_analytics.txt");
						 
									Map<String, Object> properties4 = Plist.load("C:/Results/appConfig.plist");
									
									for (Map.Entry<String, Object> entry : properties4.entrySet()) {
									if(entry.getKey().contains("apiKeys")) {
									Key1 =entry.getKey(); 
									nextKey = entry.getValue();
										}
									}
										
										Prod = Plist.toPlist(nextKey);
										file = new File("C:/Results/app1.plist");
										writer=new FileWriter(file);
										writer.write(Prod);
										writer.close();
											
										Map<String, Object> prod2 = Plist.load("C:/Results/app1.plist");
									
										for (Map.Entry<String, Object> entry1 : prod2.entrySet()) {
										if(entry1.getKey().contains("prod")) {
									
										nextKey = entry1.getValue();
												}
										}
										
									
										finalprodkey = Plist.toPlist(nextKey);
										file = new File("C:/Results/app1.plist");
										writer=new FileWriter(file);
										writer.write(finalprodkey);
										writer.close();	
										
										Map<String, Object> finalkey2 = Plist.load("C:/Results/app1.plist");
							
										for (Map.Entry<String, Object> entry : finalkey2.entrySet()) {
										if(entry.getKey().contains("GAAnalyticsKey")) {	
										 GA_Key_from_code = entry.getValue();
										}
								}
								
											System.out.println(GA_Key_from_code);


									
							        BufferedReader br = new BufferedReader(new FileReader("C:/Results/Google_analytics.txt"));
							     
							        
							try {
								
						
							        RestAssured.baseURI = "https://www.googleapis.com";
							        while ((Token = br.readLine()) != null) {
							        	GA_Response1 = given().header("Authorization","Bearer "+ Token).
							        			when().
							        			get("/analytics/v3/management/accounts/~all/webproperties");
								    		 //.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().asString();
								}
							        if(GA_Response1.getStatusCode()==200){
							        	String Response = GA_Response1.asString();
							        //	System.out.println(Response);
										 
							        	String appname_from_cloud =  appname +": iOS";
									GA_jsn=new JSONObject(Response);
									GA_array=GA_jsn.getJSONArray("items");
											 
									for(int i=0;i<GA_array.length();i++) {
									GA_ja=GA_array.getJSONObject(i);
									if(GA_ja.getString("name").equals(appname_from_cloud)) {
									GA_Key_from_cloud = GA_ja.getString("id");
								 }
								}
				
						
					
									//	Response1.then().assertThat().body("items[].name", hasItems(appname));
									}
									else {
									FileWriter writer=new FileWriter(file_GAKEY);
									RestAssured.baseURI ="https://accounts.google.com";
									String GA_res = given().header("Content-Type", "application/x-www-form-urlencoded").formParam("refresh_token", "1/D8IJbZTdiP_ZGWBhoGtXMoh7-dYVi0AVcavnOUKx8YE").formParam("grant_type", "refresh_token").formParam("client_secret", "gidHVSbBwhox7ac0TV1_LhfQ").formParam("client_id", "931206466066-82vq5f88fhk20msgibu7a5bkbgs38u2v.apps.googleusercontent.com").
									when().
									post("/o/oauth2/token").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().asString();
									//System.out.println(GA_res);
									JsonPath GA_js = new JsonPath(GA_res);
									String new_token = GA_js.get("access_token");
									//System.out.println(new_token);
									writer.write(new_token);
									writer.close();
									br = new BufferedReader(new FileReader("C:/Results/Google_analytics.txt"));
									RestAssured.baseURI = "https://www.googleapis.com";
									while ((Token = br.readLine()) != null) {
									GA_Response2 = given().header("Authorization","Bearer "+ Token).
									when().
									get("/analytics/v3/management/accounts/~all/webproperties")
									.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().asString();
								}
									//System.out.println(GA_Response2);
									//String appname_from_cloud = BranchKey.appfilename.replaceAll(BranchKey.appfilename, BranchKey.appfilename+": iOS");
									GA_jsn=new JSONObject(GA_Response2);
									GA_array=GA_jsn.getJSONArray("items");
									for(int i=0;i<GA_array.length();i++) {
									GA_ja=GA_array.getJSONObject(i);
									if(GA_ja.getString("name").equals(appname+": iOS")) {
									GA_Key_from_cloud = GA_ja.getString("id");
									}
								}
									
								}
							}catch(Throwable t) {
								t.getStackTrace();
							}
							        //System.out.println("The API key from the cloud are:"+GA_Key_from_cloud);
									//System.out.println("The  key from the code are:" + GA_Key_from_code );
									
								
									if(GA_Key_from_code.equals (GA_Key_from_cloud)) {
									//System.out.println("Test case passed: Key Matches");
										logger.log(LogStatus.INFO, "Google Analytics Code Key: "+ GA_Key_from_code);
										logger.log(LogStatus.INFO, "Google Analytics Cloud Key: "+ GA_Key_from_cloud);
										logger.log(LogStatus.PASS, "<font color = Green> Google Analytics Code Key and Google Analytics Cloud Key are a MATCH ");
									}else {
									//System.out.println("Test case failed: Key do not Match");
										logger.log(LogStatus.INFO, "Google Analytics Code Key: "+ GA_Key_from_code);
										logger.log(LogStatus.INFO, "Google Analytics Cloud Key: "+ GA_Key_from_cloud);
										logger.log(LogStatus.FAIL, "<font color = Red>  Google Analytics Code Key and Google Analytics Cloud Key do not MATCH ");
								}
									
									br.close();
									extent.endTest(logger);
									extent.flush();
									System.setProperty("webdriver.chrome.driver","C:/Results/chromedriver.exe");
								       WebDriver driver=new ChromeDriver();
								       driver.manage().timeouts().pageLoadTimeout(100000, TimeUnit.MILLISECONDS);
								       String path=("file:///C:/Results/Report.html");
								       driver.get(path);
									String windowHandle = driver.getWindowHandle();
									driver.switchTo().window(windowHandle);
									driver.manage().window().maximize();
								
	}

		
	
		

		
		

}