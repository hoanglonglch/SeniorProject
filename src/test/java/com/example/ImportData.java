package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;

public class ImportData {
	public static List<Hospital> saveHospital(String fileName){
		List<Hospital> hospitals = new ArrayList<>();
		JSONParser parse = new JSONParser();
		Resource resource = new ClassPathResource("static/bus_route/"+fileName+".json");
		try {
			File file = resource.getFile();
			Object obj = parse.parse(new FileReader(file.toString()));
			String jsonString = obj.toString();
			JSONArray jsonArray = new JSONArray(jsonString);
			for(int i=0; i<jsonArray.length(); i++){
				Hospital hospital = new Hospital(jsonArray.getJSONObject(i).getInt("id"),
							jsonArray.getJSONObject(i).getString("name"),
							jsonArray.getJSONObject(i).getString("address"),
							jsonArray.getJSONObject(i).getDouble("lat"),
							jsonArray.getJSONObject(i).getDouble("lng"));
				hospitals.add(hospital);
			}
			return hospitals;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
	public static List<Bus> saveBus(String fileName){
		List<Bus> buses = new ArrayList<>();
		JSONParser parse = new JSONParser();
		Resource resource = new ClassPathResource("static/bus_route/"+fileName+".json");
		try {
			File file = resource.getFile();
			Object obj = parse.parse(new FileReader(file.toString()));
			String jsonString = obj.toString();
			JSONArray jsonArray = new JSONArray(jsonString);
			for(int i=0; i<jsonArray.length(); i++){
				Bus bus= new Bus(jsonArray.getJSONObject(i).getInt("id"),
							jsonArray.getJSONObject(i).getString("name"),null,
							jsonArray.getJSONObject(i).getDouble("lat"),
							jsonArray.getJSONObject(i).getDouble("lng"));
				buses.add(bus);
			}
			return buses;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		List<Bus> buses= saveBus("busStation");
		for (Bus bus : buses) {
			System.out.println(bus.getName());
		}
	} 
	
}
