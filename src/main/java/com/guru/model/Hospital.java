package com.guru.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hospital")
public class Hospital {
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String address;
	private double lat;
	private double lng;
	private String phone;
	
	public Hospital(int id, String name, String address, double lat, double lng, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Hospital() {
		// TODO Auto-generated constructor stub
	}
	public Hospital(int id, String name, String address, double lat, double lng) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
}
