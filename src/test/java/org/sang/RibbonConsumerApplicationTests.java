package org.sang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sang.Entity.Book;
import org.sang.Controller.ConsumerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

@SpringBootTest(classes={RibbonConsumerApplication.class,RibbonConsumerApplicationTests.class})
@RunWith(SpringRunner.class)
public class RibbonConsumerApplicationTests {


    @Autowired
    private ConsumerController consumerController;
    @Autowired
    private RestTemplate restTemplate;
    @Test
    public void contextLoads() throws ExecutionException, InterruptedException {

        Book book = consumerController.test1();

        System.out.println(book.toString());
    }

}
