package com.csi.sbs.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.investment.InvestmentApplication;

import org.springframework.context.ApplicationContext;

//import com.csi.sbs.common.business.log.InitLog;

@SpringBootApplication
@EnableEurekaClient
public class InvestmentApplication {
	
	
	@Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext context = SpringApplication.run(InvestmentApplication.class, args);
		//InitLog.loadLogConfig(context,"investment");//初始化日志相关配置
	}

}
