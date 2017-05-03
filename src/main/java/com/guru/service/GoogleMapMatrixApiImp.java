package com.guru.service;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;

public class GoogleMapMatrixApiImp implements IGoogleMapMatrixApi{
	
//	@Autowired
//	get data from xml file?
	
//	GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCv7lDkj6Yd3cMbujJcHTKIo_AzLEga-7c");
	GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyA_C3SBwk2NQSZjwjq-9XVdWH2hy1vue3g");
//	GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDfR6OaUcU8MB1BdRuweZMJrjNVjdDZUOw");
	
	@Override
	public DistanceMatrix getDistanceMatrixUser(String[] origins, String[] destinations) {
		DistanceMatrix distanceMatrix=null;;
		try {
			distanceMatrix = DistanceMatrixApi.getDistanceMatrix(this.context, origins, destinations).await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return distanceMatrix;
	}

	@Override
	public GeocodingResult[] geocodeFromAddress(String address) {
		GeocodingResult[] results=null;
		try {
			results = GeocodingApi.geocode(this.context,address).await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

}
