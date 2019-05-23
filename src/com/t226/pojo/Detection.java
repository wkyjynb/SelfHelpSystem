package com.t226.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Detection {
	private int temperature;
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public int getSmoke() {
		return smoke;
	}
	public void setSmoke(int smoke) {
		this.smoke = smoke;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private int humidity;
	private int smoke;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date date;
}
