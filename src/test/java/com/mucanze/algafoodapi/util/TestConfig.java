package com.mucanze.algafoodapi.util;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
	
	@Bean
	public DatabaseCleaner databaseCleaner() {
		return new DatabaseCleaner();
	}

}
