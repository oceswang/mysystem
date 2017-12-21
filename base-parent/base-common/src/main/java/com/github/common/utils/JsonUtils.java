package com.github.common.utils;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils
{
	private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

	private static ObjectMapper createObjectMapper()
	{
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		objectMapper.registerModule(new JavaTimeModule());

		return objectMapper;
	}

	public static String object2Json(Object obj)
	{
		StringWriter sw = new StringWriter();
		JsonGenerator gen = null;
		try
		{
			gen = new JsonFactory().createGenerator(sw);
			OBJECT_MAPPER.writeValue(gen, obj);
		} catch (IOException e)
		{
			throw new RuntimeException("不能序列化对象为Json", e);
		} finally
		{
			if (null != gen)
			{
				try
				{
					gen.close();
				} catch (IOException e)
				{
					throw new RuntimeException("不能序列化对象为Json", e);
				}
			}
		}
		return sw.toString();
	}

	public static <T> T json2Object(String json, Class<T> clazz)
	{
		try
		{
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (IOException e)
		{
			throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json + ", Class 是" + clazz, e);
		}
	}
}
