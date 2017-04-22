package com.guru.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
			model.addAttribute("station","Đồn Công An");
			model.addAttribute("stations", serviceMap.getPoliceStations());
			break;
		case 2:
			model.addAttribute("station","Bệnh Viện");
			model.addAttribute("stations", serviceMap.getHospitalStations());
			break;
		case 3:
			model.addAttribute("station","WC");
			model.addAttribute("stations", serviceMap.getWcStations());
			break;
		case 4:
			model.addAttribute("station","Trạm Xe Buýt");
			model.addAttribute("stations",serviceMap.getBusStations());
			break;
		}
		return "stations_map";
	}

}
