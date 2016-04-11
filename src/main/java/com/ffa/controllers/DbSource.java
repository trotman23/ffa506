package com.ffa.controllers;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Connection;

@Component
public final class DbSource {

	private static final DbSource INSTANCE = new DbSource();
	
	private static ComboPooledDataSource dataSource;
	
	private DbSource() {
		dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://aaxd0xqcmiqmgi.c0fxx6pghrju.us-west-2.rds.amazonaws.com/ffadb");
			dataSource.setUser("master");
			dataSource.setPassword("eric2mad");
			dataSource.setMaxPoolSize(1000);
			dataSource.setMinPoolSize(10);
			dataSource.setAcquireIncrement(10);
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	public static DbSource getInstance(){
		return INSTANCE;
	}

	@Bean
	public static ComboPooledDataSource getDataSource() {
		return dataSource;
	}
	
}
