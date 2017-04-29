package com.guru.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;
import com.guru.model.WC;
import com.guru.repository.BusStationRepository;
import com.guru.repository.HospitalRepository;
import com.guru.repository.PoliceRepository;
import com.guru.repository.WCRepository;

@Service
public class MapServiceImpl<E> implements IMapService {

	@Autowired
	PoliceRepository repositoryPolice;
	
	@Autowired
	HospitalRepository repositoryHos;
	
	@Autowired
	WCRepository repoWc;
	
	@Autowired
	BusStationRepository repoBus;
	
	IGoogleMapMatrixApi ggMatrix = new GoogleMapMatrixApiImp();


	@Override
	public List<Police> getPoliceStations() {
		List<Police> policeStations = repositoryPolice.findAll();
		return policeStations;
	}

	@Override
	public List<Hospital> getHospitalStations() {
		List<Hospital> hospitalStations=repositoryHos.findAll();
		return hospitalStations;
	}

	@Override
	public List<WC> getWcStations() {
		List<WC> wCs= repoWc.findAll();
		return wCs;
	}

	@Override
	public List<Bus> getBusStations() {
		List<Bus> buses= repoBus.findAll();
		return buses;
	}

	@Override
	public List<LatLng> findNearlyPolice(String startPoint) {
		List<Police> polices= new ArrayList<>();
		
		GeocodingResult[] result = ggMatrix.geocodeFromAddress(startPoint);
		LatLng originLatLng = new LatLng(result[0].geometry.location.lat, result[0].geometry.location.lng);
		
		String originLatLngStr = originLatLng.lat + "," + originLatLng.lng;
		String[] destinations = {};
		String[] origins = { originLatLngStr };
		String latLng = "";
		/*List<Police> 
		int size=repositoryPolice.findAll().size();
		
		for(int i=0;i<size;i++){
			latLng = .get(i).getLat() + "," + nearlyBStations.get(i).getLng();
			destinations = this.addElement(destinations, latLng);
			destinationsLength = destinations.length;
			if (size== 100) {
				
			}else if(size < 100 && i == size-1){
				
			}
		}*/
		return null;
	}

	@Override
	public List<LatLng> findNearlyBus(String startPoint,List<Bus> listPolice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LatLng> findNearlyWc(String startPoint,List<WC> listPolice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LatLng> findNearlyHospital(String startPoint,List<Hospital> listPolice) {
		// TODO Auto-generated method stub
		return null;
	}
	public String[] addElement(String[] strArr, String element) {
		strArr = Arrays.copyOf(strArr, strArr.length + 1);
		strArr[strArr.length - 1] = element;
		return strArr;
	}

	
}
