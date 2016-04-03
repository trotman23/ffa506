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
	
	private static ComboPooledDataSource DATA_SOURCE;
	
	private DbSource() {
		DATA_SOURCE = new ComboPooledDataSource();
		try {
			DATA_SOURCE.setDriverClass("com.mysql.jdbc.Driver");
			DATA_SOURCE.setJdbcUrl("jdbc:mysql://localhost/ffadb");
			DATA_SOURCE.setUser("root");
			DATA_SOURCE.setPassword("eric2mad");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	public static DbSource getInstance(){
		return INSTANCE;
	}

	@Bean
	public static ComboPooledDataSource getDataSource() {
		return DATA_SOURCE;
	}
	
}
