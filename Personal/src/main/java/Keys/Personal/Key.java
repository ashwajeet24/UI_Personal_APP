package Keys.Personal;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import xmlwise.Plist;

public class Key {
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
			Key =entry.getKey(); 
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
}
