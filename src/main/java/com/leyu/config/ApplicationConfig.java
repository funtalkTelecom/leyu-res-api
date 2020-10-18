package com.leyu.config;

import com.leyu.config.interceptors.APIInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
//@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter{
	
    @Autowired private APIInterceptor apiInterceptor;
	
    public void addInterceptors(InterceptorRegistry registry) {

    	InterceptorRegistration ir=registry.addInterceptor(apiInterceptor).addPathPatterns("/**");//拦截
        super.addInterceptors(registry);
    }


	public Executor getAsyncExecutor() {
		//使用Spring内置线程池任务对象
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();
        return taskExecutor;
	}
    
}
