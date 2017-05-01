package com.example;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.maps.model.LatLng;
import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;
import com.guru.model.WC;
import com.guru.repository.BusStationRepository;
import com.guru.repository.HospitalRepository;
import com.guru.repository.PoliceRepository;
import com.guru.repository.WCRepository;
import com.guru.service.IMapService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJPARepository {
	
	/*@Autowired
	BusRouteRepository repositoryBusRoute;*/
	
	/*@Autowired
	BusStationRepository repositoryBusStation;*/
	
	@Autowired
	PoliceRepository repositoryPolice;
	
	/*@Autowired
	HospitalRepository repositoryHos;*/
	
	/*@Autowired 
	BusStationRepository repositoryBus;*/
	
	@Autowired
	WCRepository reposWc;

	@Autowired
	IMapService serviceMap;
	
	@Test
	public void imporData(){
		List<LatLng> latLngs= serviceMap.findNearlyPolice("135 cù chính lan,da nang");
		assertEquals(2, latLngs.size());
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
	
}
