package umg.simulacion.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

public class XmlTest {
	
	public static void main(String[] args) {
		String excelFilePath = "C:\\Users\\escritorio\\Desktop\\testxml.xml";
		String line="";
		String str="";
        try {
			 	
		        BufferedReader br = new BufferedReader(new FileReader(excelFilePath));
		        while ((line = br.readLine()) != null) 
		        {   
		            str+=line;  
		        }
		       
		        
			
		        JSONObject jsondata = XML.toJSONObject(str);
			/*String json = XML.toJSONObject(inpu).toString();*/
			
			
			ObjectMapper mapper = new ObjectMapper();
			ObjectJson data = mapper.readValue(jsondata.toString(), ObjectJson.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
