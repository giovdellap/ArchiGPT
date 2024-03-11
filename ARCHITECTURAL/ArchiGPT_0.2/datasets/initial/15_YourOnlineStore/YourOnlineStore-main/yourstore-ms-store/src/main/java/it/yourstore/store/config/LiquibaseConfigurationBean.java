package it.yourstore.store.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfigurationBean {

	@Autowired
	private DataSource dataSource;

	@Bean
	public LiquibaseProperties liquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean
	public CustomSpringLiquibase liquibase() {
		LiquibaseProperties liquibaseProperties = liquibaseProperties();
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:db/liquibase/update.xml");
		liquibase.setContexts(liquibaseProperties.getContexts());
		liquibase.setDataSource(getDataSource(liquibaseProperties));
		liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
		liquibase.setDropFirst(liquibaseProperties.isDropFirst());
		liquibase.setShouldRun(true);
		liquibase.setLabels(liquibaseProperties.getLabels());
		liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
		return new CustomSpringLiquibase(liquibase);
	}

	private DataSource getDataSource(LiquibaseProperties liquibaseProperties) {
		if (liquibaseProperties.getUrl() == null) {
			return this.dataSource;
		}
		return DataSourceBuilder.create().url(liquibaseProperties.getUrl()).username(liquibaseProperties.getUser())
				.password(liquibaseProperties.getPassword()).build();
	}
}
