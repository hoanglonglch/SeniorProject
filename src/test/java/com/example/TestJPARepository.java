package com.example;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.guru.model.Police;
import com.guru.model.WC;
import com.guru.repository.PoliceRepository;
import com.guru.repository.WCRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJPARepository {
	
	/*@Autowired
	BusRouteRepository repositoryBusRoute;*/
	
	/*@Autowired
	BusStationRepository repositoryBusStation;*/
	
	@Autowired
	PoliceRepository repositoryPolice;
	
	@Test
	public void imporData(){
	/*	List<Police> polices=saveBusRoute("police");
		for (Police police : polices) {
			repositoryPolice.save(police);
		}
		assertEquals(26, repositoryPolice.findAll().size());*/
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
	public static List<WC> saveWC(String fileName ){
		List<WC> polices= new ArrayList<>();
		JSONParser parse= new JSONParser();
		Resource resource= new ClassPathResource("static/bus_route/"+fileName+".json");
		try {
			File file= resource.getFile();
			Object obj = parse.parse(new FileReader(file.toString()));
			String jsonString=obj.toString();
			
			JSONArray jsonArray= new JSONArray(jsonString);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				WC police= new WC(jsonArray.getJSONObject(i).getInt("id"),
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
	
}
