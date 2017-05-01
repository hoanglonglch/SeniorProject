package com.guru.model;

public class RouteElement {
	private String name;
	private String address;
	private double lat;
	private double lng;

	public RouteElement() {
		// TODO Auto-generated constructor stub
	}
	public RouteElement(String name, String address, double lat, double lng) {
		super();
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
