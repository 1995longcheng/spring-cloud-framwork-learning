package com.learning.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.learning.common.BaseApplication;
import com.learning.nacos.configuration.FamilyConfiguration;

@EnableConfigurationProperties(value = { FamilyConfiguration.class })
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.learning")
public class NacosProviderWithoutSercutiyApplication extends BaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(NacosProviderWithoutSercutiyApplication.class, args);
	}
}
