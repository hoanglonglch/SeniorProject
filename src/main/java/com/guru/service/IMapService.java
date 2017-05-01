package com.guru.service;

import java.util.List;

import com.google.maps.model.LatLng;
import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;
import com.guru.model.RouteElement;
import com.guru.model.WC;

public interface IMapService {
	 List<Police> getPoliceStations();
	 List<Hospital> getHospitalStations();
	 List<WC> getWcStations();
	 List<Bus> getBusStations();
	 
	 List<LatLng> findNearlyPolice(String startPoint);
	 List<RouteElement> findNearlyPolice1(String originAddress);

}
