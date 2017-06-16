package com.fly.shutter;

public class Site_Visit {

	private String page_id;
	private String event_time;
	public String customer_id;
	private String tags;

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getPage_id() {
		return page_id;
	}

	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}

	public String getEvent_time() {
		return event_time;
	}

	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String string) {
		this.tags = string;
	}

}
