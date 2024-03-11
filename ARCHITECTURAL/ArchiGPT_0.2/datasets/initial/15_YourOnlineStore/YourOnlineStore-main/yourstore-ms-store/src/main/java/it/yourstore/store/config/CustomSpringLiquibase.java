package it.yourstore.store.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

public class CustomSpringLiquibase implements InitializingBean, BeanNameAware, ResourceLoaderAware {

	private static final Logger LOGGER = LogManager.getLogger(CustomSpringLiquibase.class);
	private SpringLiquibase springLiquibase;

	public CustomSpringLiquibase(SpringLiquibase liquibase) {
		springLiquibase = liquibase;
	}

	@Override
	public void afterPropertiesSet() {
		try {
			springLiquibase.afterPropertiesSet();
		} catch (LiquibaseException e) {
			LOGGER.error("LiquibaseConfig ", e);
		}
	}

	@Override
	public void setBeanName(String name) {
		springLiquibase.setBeanName(name);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		springLiquibase.setResourceLoader(resourceLoader);
	}
}
