package com.guru.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guru.model.Bus;
import com.guru.model.Hospital;
import com.guru.model.Police;
import com.guru.model.WC;
import com.guru.repository.BusStationRepository;
import com.guru.repository.HospitalRepository;
import com.guru.repository.PoliceRepository;
import com.guru.repository.WCRepository;

@Service
public class MapServiceImpl implements IMapService {

	@Autowired
	PoliceRepository repositoryPolice;
	
	@Autowired
	HospitalRepository repositoryHos;
	
	@Autowired
	WCRepository repoWc;
	
	@Autowired
	BusStationRepository repoBus;

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
}
