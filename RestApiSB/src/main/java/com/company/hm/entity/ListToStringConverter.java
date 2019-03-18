package com.company.hm.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.commons.lang3.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListToStringConverter.class);

	
	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		LOGGER.debug("ConvertToDatabaseColumn::::: {} ",attribute);

		if (attribute == null || attribute.isEmpty()) {
			return "";
		}
		LOGGER.debug("Conversion to DB column atribute: {}", attribute);

		return StringUtils.join(attribute, ",");
	}

	
	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		LOGGER.debug("ConvertToEntityAttribute::::::: {}", dbData);

		if (dbData == null || dbData.trim().length() == 0) {
			return new ArrayList<String>();
		}
		LOGGER.debug("Conversion to DB column atribute: {}", dbData);

		String[] data = dbData.split(",");
		return Arrays.asList(data);
	}
}