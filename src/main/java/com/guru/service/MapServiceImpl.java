package com.guru.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;
import com.guru.model.RouteElement;
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
		List<Hospital> hospitalStations = repositoryHos.findAll();
		return hospitalStations;
	}

	@Override
	public List<WC> getWcStations() {
		List<WC> wCs = repoWc.findAll();
		return wCs;
	}

	@Override
	public List<Bus> getBusStations() {
		List<Bus> buses = repoBus.findAll();
		return buses;
	}

	@Override
	public List<LatLng> findNearlyPolice(String originAddress) {
		List<Police> policeTemp = new ArrayList<>();
		List<Police> polices = repositoryPolice.findAll();
		List<LatLng> latLngs=new ArrayList<>();
		int size = polices.size();

		GeocodingResult[] result = ggMatrix.geocodeFromAddress(originAddress);
		LatLng originLatLng = new LatLng(result[0].geometry.location.lat, result[0].geometry.location.lng);
		latLngs.add(originLatLng);
		String originLatLngStr = originLatLng.lat + "," + originLatLng.lng;
		String destinations = "";
		String latLng = "";
		String[] destiationArr = {};
		String[] origins = { originLatLngStr };
		DistanceMatrix matrix;
		List<Integer> realDistances = new ArrayList<>();
		int realDistance = 0;
		int matrixLength;

		for (int i = 0; i < size; i++) {
			destinations += polices.get(i).getLat() + "," + polices.get(i).getLng() + "|";
		}
		destiationArr = destinations.split("\\|");

		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				matrix = ggMatrix.getDistanceMatrixUser(origins, destiationArr);
				matrixLength = matrix.rows[0].elements.length;
				for (int j = 0; j < matrixLength; j++) {
					realDistance = (int) matrix.rows[0].elements[j].distance.inMeters;
//					System.out.println(j+" real distance "+realDistance);
					realDistances.add(realDistance);
				}
				destiationArr = new String[] {};
			}
		}
		int min=0;
		int index=0;
		min=realDistances.get(0);
		for (int i = 0; i < realDistances.size()-1; i++) {
			if(min>realDistances.get(i)){
				min=realDistances.get(i);
				index=i;
			}
		}
		latLngs.add(new LatLng(polices.get(index).getLat(),polices.get(index).getLng()));
		return latLngs;
	}

	@Override
	public List<RouteElement> findNearlyPolice1(String originAddress) {
		List<Police> polices = repositoryPolice.findAll();
		List<RouteElement> routeElements=new ArrayList<>();
		int size = polices.size();

		GeocodingResult[] result = ggMatrix.geocodeFromAddress(originAddress);
		LatLng originLatLng = new LatLng(result[0].geometry.location.lat, result[0].geometry.location.lng);
		routeElements.add(new RouteElement("origin point", "origin", originLatLng.lat,originLatLng.lng));
		String originLatLngStr = originLatLng.lat + "," + originLatLng.lng;
		String destinations = "";
		String latLng = "";
		String[] destiationArr = {};
		String[] origins = { originLatLngStr };
		DistanceMatrix matrix;
		List<Integer> realDistances = new ArrayList<>();
		int realDistance = 0;
		int matrixLength;

		for (int i = 0; i < size; i++) {
			destinations += polices.get(i).getLat() + "," + polices.get(i).getLng() + "|";
		}
		destiationArr = destinations.split("\\|");

		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				matrix = ggMatrix.getDistanceMatrixUser(origins, destiationArr);
				matrixLength = matrix.rows[0].elements.length;
				for (int j = 0; j < matrixLength; j++) {
					realDistance = (int) matrix.rows[0].elements[j].distance.inMeters;
//					System.out.println(j+" real distance "+realDistance);
					realDistances.add(realDistance);
				}
				destiationArr = new String[] {};
			}
		}
		int min=0;
		int index=0;
		min=realDistances.get(0);
		for (int i = 0; i < realDistances.size()-1; i++) {
			if(min>realDistances.get(i)){
				min=realDistances.get(i);
				index=i;
			}
		}
		routeElements.add(new RouteElement(polices.get(index).getName(), polices.get(index).getAddress(),polices.get(index).getLat(), polices.get(index).getLng()));
		return routeElements;
	}
}
