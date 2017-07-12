package com.galaxyinternet.common.web.resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public class ResponseData <T>
{
	private ResponseStatus status = ResponseStatus.OK;
	private String message;
	private T value;
	private List<T> values;
	private Page<T> page;
	private Map<String,Object> others = new HashMap<>();
	public ResponseStatus getStatus()
	{
		return status;
	}
	public void setStatus(ResponseStatus status)
	{
		this.status = status;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public T getValue()
	{
		return value;
	}
	public void setValue(T value)
	{
		this.value = value;
	}
	public List<T> getValues()
	{
		return values;
	}
	public void setValues(List<T> values)
	{
		this.values = values;
	}
	public Map<String, Object> getOthers()
	{
		return others;
	}
	public void setOthers(Map<String, Object> others)
	{
		this.others = others;
	}
	public Page<T> getPage()
	{
		return page;
	}
	public void setPage(Page<T> page)
	{
		this.page = page;
	}
	
}
