package com.t226.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
/**
 * 自定义转换器
 * @author LXW
 *
 */
public class StringToDateConverter implements Converter<String,Date> {

	private String dataPattern;
	
	public StringToDateConverter(String dataPattern) {//定义构造注入方法
		this.dataPattern = dataPattern;
	}

	@Override
	public Date convert(String s) {
		// TODO Auto-generated method stub
		Date date=null;
	try {
		date=new SimpleDateFormat(dataPattern).parse(s);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return date;
	}

}
