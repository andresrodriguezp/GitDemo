package Automation.data;



import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class dataReader {
	
	public List<HashMap<String,String>> getJsonDataToMap() throws IOException
	{
		//here we are importing a json file and converting it to string
		//we are creating a new file which is located in the location provider that is the user space plus the project location
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//test//java//Automation//data//PurchaseOrder.json"),StandardCharsets.UTF_8);
		
		// now we are going to convert the string to hashmap
		// first we create an object to the class object mapper, this class allows us to convert from json to javaobjects
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
		return data;
	}

}
