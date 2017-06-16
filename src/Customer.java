package com.fly.shutter;

import java.util.ArrayList;

public class Customer {

	String Key;
	String event_time;
	private Integer week1;
	private ArrayList <Order> addweekorder = new ArrayList();
	private ArrayList <Site_Visit> sitevisits = new ArrayList();
	private ArrayList <Image> images = new ArrayList();
	
	private ArrayList <Order> orders = new ArrayList();
	public ArrayList<Site_Visit> getSitevisits() {
		return this.sitevisits;
	}

	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	String last_name;
	String adr_city;
	String adr_state;
	
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAdr_city() {
		return adr_city;
	}
	public void setAdr_city(String adr_city) {
		this.adr_city = adr_city;
	}
	public String getAdr_state() {
		return adr_state;
	}
	public void setAdr_state(String adr_state) {
		this.adr_state = adr_state;
	}
	
	public void addsitevisit(Site_Visit sitevisit)
	{
		if(sitevisit != null)
		{
			this.sitevisits.add(sitevisit);
		}
	}
	
	public void addImage(Image img)
	{
		if(img != null)
		{
			this.images.add(img);
		}
	}

	public ArrayList<Image> getImages() {
		return this.images;
	}
	
	
	public void addOrder(Order order) {
		if(order != null){
			this.addweekorder.add(order);
		}
	}
	
	public void addweekOrder(Order order){
		if(order !=null)
		{
			this.orders.add(order);
		}
	}
	
	
	public ArrayList<Order> getOrders() {
		return this.orders;
	}

	public void updateOrder(Order order) {
		if(order != null){
			int flag = -1;
			for(Order id : orders)
	        {
	            if(id.getKey().equals(order.getKey()))
	                flag = orders.indexOf(id);
	        }
			if(flag != -1){
				this.orders.set(flag, order);
			}
		}
	}

	public Integer getWeek1() {
		return week1;
	}

	public void setWeek1(Integer week1) {
		this.week1 = week1;
	}

	public ArrayList <Order> getAddweekorder() {
		return this.addweekorder;
	}

	public void setAddweekorder(ArrayList <Order> addweekorder) {
		this.addweekorder = addweekorder;
	}

}
