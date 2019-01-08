package com.przemo.entity;

public class TablePlayer
{
	String name;
	String time;
	
	public TablePlayer()
	{
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public TablePlayer(String name, String time) {
		super();
		this.name = name;
		this.time = time;
	}
	
}
