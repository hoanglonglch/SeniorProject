package com.example;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.guru.model.Police;

public class ImportData {
	
	
	public static List<Police> saveBusRoute(String fileName ){
		List<Police> polices= new ArrayList<>();
		JSONParser parse= new JSONParser();
		Resource resource= new ClassPathResource("static/bus_route/"+fileName+".json");
		try {
			File file= resource.getFile();
			Object obj = parse.parse(new FileReader(file.toString()));
			String jsonString=obj.toString();
			
			JSONArray jsonArray= new JSONArray(jsonString);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				Police police= new Police(jsonArray.getJSONObject(i).getInt("id"),
						jsonArray.getJSONObject(i).getString("name"),
						jsonArray.getJSONObject(i).getString("address"),
						jsonArray.getJSONObject(i).getDouble("lat"),
						jsonArray.getJSONObject(i).getDouble("lng"));
				polices.add(police);
			}
			return polices;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		List<Police> polices= saveBusRoute("Police");
		for (Police police : polices) {
			System.out.println(police.getName());
		}
	} 
	
}
