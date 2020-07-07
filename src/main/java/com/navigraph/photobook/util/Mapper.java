package com.navigraph.photobook.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

public class Mapper {
	/**
	 *
	 * @param entity
	 * @param data
	 * @return
	 * @throws IntrospectionException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	static ArrayList<?> mapList(Class<?> entity, ArrayList<Map<String, Object>> data)
			throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Map<String, Method> methodsMap = new HashMap<String, Method>();
		ArrayList<Object> mappedList = new ArrayList<Object>();
		Arrays.asList(Introspector.getBeanInfo(entity).getPropertyDescriptors()).forEach(propertyDescriptor -> {
			if (propertyDescriptor.getWriteMethod() != null) {
				if (propertyDescriptor.getWriteMethod() != null
						&& propertyDescriptor.getWriteMethod().getAnnotation(Column.class) != null) {
					methodsMap.put(propertyDescriptor.getWriteMethod().getAnnotation(Column.class).name(),
							propertyDescriptor.getWriteMethod());

				} else {
					methodsMap.put(propertyDescriptor.getName(), propertyDescriptor.getWriteMethod());
				}
			}
		});
		for (Map<String, Object> dataRow : data) {
			Object mappedData = entity.getConstructor().newInstance();
			methodsMap.forEach((columnName, method) -> {
				try {
					if (dataRow.containsKey(columnName) && dataRow.get(columnName) != null) {

						method.invoke(mappedData, dataRow.get(columnName));

					}
				} catch (Exception e) {
					if (dataRow.get(columnName) != null) {
						handleException(e, columnName);
					}
				}
			});
			mappedList.add(mappedData);
		}
		return mappedList;
	}

	/**
	 *
	 * @param entity
	 * @param data
	 * @return
	 * @throws IntrospectionException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	static Object mapObject(Class<?> entity, Map<String, Object> data)
			throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Map<String, Method> methodsMap = new HashMap<String, Method>();
		Arrays.asList(Introspector.getBeanInfo(entity).getPropertyDescriptors()).forEach(propertyDescriptor -> {
			if (propertyDescriptor.getWriteMethod() != null) {
				if (propertyDescriptor.getWriteMethod() != null
						&& propertyDescriptor.getWriteMethod().getAnnotation(Column.class) != null) {
					methodsMap.put(propertyDescriptor.getWriteMethod().getAnnotation(Column.class).name(),
							propertyDescriptor.getWriteMethod());

				} else {
					methodsMap.put(propertyDescriptor.getName(), propertyDescriptor.getWriteMethod());
				}
			}
		});
		Object mappedData = entity.getConstructor().newInstance();
		methodsMap.forEach((columnName, method) -> {
			try {
				if (data.containsKey(columnName) && data.get(columnName) != null) {

					method.invoke(mappedData, data.get(columnName));

				}
			} catch (Exception e) {
				if (data.get(columnName) != null) {
					handleException(e, columnName);
				}
			}
		});
		return mappedData;
	}

	/**
	 *
	 * @param exception
	 * @param columnName
	 */
	static void handleException(Exception e, String columnName) {
		System.err.println("Exception while setting value of ${columnName}, skipping to populate the value.");
		e.printStackTrace();
	}
}