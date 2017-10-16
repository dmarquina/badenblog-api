package com.badenblog;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "properties")
	public PropertiesFactoryBean loadProperties() {
	  final PropertiesFactoryBean bean = new PropertiesFactoryBean();
	  final Resource[] resources = new Resource[] {
	          new ClassPathResource("/sql-template/badenblog-sql.xml") };
	  bean.setLocations(resources);
	  return bean;
	}
}
