package com.ch.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtils {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> clz) {
		return applicationContext.getBean(clz);
	}

}
