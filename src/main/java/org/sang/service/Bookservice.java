package org.sang.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.sang.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;


@Service
public class Bookservice {

    private static String url = "http://HELLO-SERVICE/";

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand
    public Future<Book> test2() {
        AsyncResult asyncResult = new AsyncResult<Book>() {
            @Override
            public Book invoke() {
                return restTemplate.getForEntity(url + "/providerBook", Book.class).getBody();
            }
        };
        return asyncResult;
    }
}
