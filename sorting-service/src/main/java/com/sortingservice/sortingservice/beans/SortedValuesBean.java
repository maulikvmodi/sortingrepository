package com.sortingservice.sortingservice.beans;

import java.util.Date;

public class SortedValuesBean {
	
	public SortedValuesBean(){
		super();
	}
	
	public SortedValuesBean(int id, String originalstring, String sortedstring, Date createddate) {
		super();
		this.id = id;
		this.originalstring = originalstring;
		this.sortedstring = sortedstring;
		this.createddate = createddate;
	}

	private int id;
	private String originalstring;
	private String sortedstring;
	private Date createddate;
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

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	@Override
	public String toString() {
		return "SortedValuesBean [id=" + id + ", originalstring=" + originalstring + ", sortedstring=" + sortedstring
				+ ", createddate=" + createddate + "]";
	}
	
	
	
}
