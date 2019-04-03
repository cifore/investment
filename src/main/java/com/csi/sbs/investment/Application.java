package com.csi.sbs.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.investment.Application;

@SpringBootApplication
@EnableEurekaClient
public class Application {
	
	@Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
