package org.sang;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableCircuitBreaker 开启短路
 * <p>
 * 可以使用此注解替代 @SpringCloudApplication 此注解包括以下注解
 * （ * @Target(ElementType.TYPE)
 * @Retention(RetentionPolicy.RUNTIME)
 * @Documented
 * @Inherited
 * @SpringBootApplication
 * @EnableDiscoveryClient
 * @EnableCircuitBreaker）
 */
//@EnableHystrix

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
//@SpringCloudApplication
public class RibbonConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonConsumerApplication.class, args);
    }

    @LoadBalanced //开启负载均衡---按照一定规则调用服务
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     *通过注解实现异步请求
     * 1:配置HystrixCommandAspect的Bean
     * 2:通过AsyncResult来执行调用
     * @return
     */
    @Bean
    public HystrixCommandAspect hystrixCommandAspect(){
        return new HystrixCommandAspect();
    }

}
