package com.sortingservice.sortingservice.beans;

import java.time.ZonedDateTime;

public class SortedValuesBean {
	
	private int id;
	private String originalstring;
	private String sortedstring;
	private ZonedDateTime createddate;
	
	public SortedValuesBean(){
		
	}
	
	public SortedValuesBean(int id, String originalstring, String sortedstring, ZonedDateTime createddate) {
		super();
		this.id = id;
		this.originalstring = originalstring;
		this.sortedstring = sortedstring;
		this.createddate = createddate;
	}
	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalstring() {
		return originalstring;
	}

	public void setOriginalstring(String originalstring) {
		this.originalstring = originalstring;
	}

	public String getSortedstring() {
		return sortedstring;
	}

	public void setSortedstring(String sortedstring) {
		this.sortedstring = sortedstring;
	}

	public ZonedDateTime getCreateddate() {
		return createddate;
	}

	public void setCreateddate(ZonedDateTime createddate) {
		this.createddate = createddate;
	}

	@Override
	public String toString() {
		return "SortedValuesBean [id=" + id + ", originalstring=" + originalstring + ", sortedstring=" + sortedstring
				+ ", createddate=" + createddate + "]";
	}
	
	
	
}
