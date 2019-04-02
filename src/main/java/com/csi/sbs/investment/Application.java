package com.csi.sbs.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.log.InitLog;

@SpringBootApplication
public class Application {
	
	
	@Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		InitLog.loadLogConfig(context,"investment");//初始化日志相关配置
	}

}
