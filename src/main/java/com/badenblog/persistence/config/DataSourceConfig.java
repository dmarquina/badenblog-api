package com.badenblog.persistence.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class DataSourceConfig {

  @Autowired
  @Bean(name = "sessionFactory")
  public SessionFactory sessionFactory(final HibernateEntityManagerFactory factory) {
    return factory.getSessionFactory();
  }

}