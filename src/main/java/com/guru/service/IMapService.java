package com.guru.service;

import java.util.List;

import com.google.maps.model.LatLng;
import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;
import com.guru.model.WC;

public interface IMapService {
	 List<Police> getPoliceStations();
	 List<Hospital> getHospitalStations();
	 List<WC> getWcStations();
	 List<Bus> getBusStations();
	 
	 List<LatLng> findNearlyPolice(String startPoint);
	 List<LatLng> findNearlyBus(String startPoint,List<Bus> listPolice);
	 List<LatLng> findNearlyWc(String startPoint,List<WC> listPolice);
	 List<LatLng> findNearlyHospital(String startPoint,List<Hospital> listPolice);
}
