package com.lap.springbootdocker.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "data")
public class DataInfo {
    
    @Id
    private String dataId;

    private Date date;

    private String type;
    private String jsonData;

    public DataInfo() {
	}

	public DataInfo(String type, Date date, String data) {
		this.type = type;
		this.date = date;
		this.jsonData = data;
	}

	public String getId() {
		return dataId;
	}

    public void setType(String t) {
		this.type = t;
	}

	public String getType() {
		return type;
	}

    public void setDate(Date t) {
		this.date = t;
	}

	public Date getDate() {
		return date;
	}

    public void setData(String d) {
		this.jsonData = d;
	}

	public String getData() {
		return jsonData;
	}
}
