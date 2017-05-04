package com.guru.controller;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.guru.service.IMapService;;

@Controller
@RequestMapping(value = "/map")
public class MapController {
	@Autowired
	public IMapService serviceMap;

	private static final Logger logger = LoggerFactory.getLogger(MapController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		return "map_offical";
	}

	@RequestMapping(value = MapURL.SPOT, method = RequestMethod.GET)
	public String spotDetailSideBar(@PathVariable("id") int id, Model model) {
		switch (id) {
		case 1:
			model.addAttribute("station", "Đồn Công An");
			model.addAttribute("stations", serviceMap.getPoliceStations());
			break;
		case 2:
			model.addAttribute("station", "Bệnh Viện");
			model.addAttribute("stations", serviceMap.getHospitalStations());
			break;
		case 3:
			model.addAttribute("station", "WC");
			model.addAttribute("stations", serviceMap.getWcStations());
			break;
		case 4:
			model.addAttribute("station", "Trạm Xe Buýt");
			model.addAttribute("stations", serviceMap.getBusStations());
			break;
		}
		return "stations_map";
	}

	@RequestMapping(value = MapURL.SPOT_MARKER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String spotDetailMarker(@RequestParam("spotId") int id, Model model) {
		String jsonString = "";
		String data = "";
		Gson gson = new Gson();
		logger.info("marker " + id);
		switch (id) {
		case 1:
			data = gson.toJson(serviceMap.getPoliceStations());
			/*
			 * JSONArray jsArr=new JSONArray(serviceMap.getPoliceStations());
			 * data=jsArr.toString();
			 */
			break;
		case 2:
			data = gson.toJson(serviceMap.getHospitalStations());
			break;
		case 3:
			data = gson.toJson(serviceMap.getWcStations());
			break;
		case 4:
			data = gson.toJson(serviceMap.getBusStations());
			break;
		}
		return data;
	}

	@RequestMapping(value = MapURL.SPOT_DIRECTION, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String spotDirection(@RequestParam("spotId1") int id,
										@RequestParam("startPoint") String startPoint) {
		String jsonString = "";
		String data = "";
		Gson gson = new Gson();
		logger.info("here "+startPoint+" "+id);
		switch (id) {
		case 1:
			data = gson.toJson(serviceMap.findNearlyPolice1(startPoint));
			break;
		case 2:
			data = gson.toJson(serviceMap.findNearlyHospital(startPoint));
			break;
		case 3:
			data = gson.toJson(serviceMap.findNearlyWc(startPoint));
			break;
		case 4:
			data = gson.toJson(serviceMap.findNearlyBusStation(startPoint));
			break;
		}
		return data;
	}
}
