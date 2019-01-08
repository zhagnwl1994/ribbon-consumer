package org.sang.Controller;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.org.apache.regexp.internal.REUtil;
import org.sang.Entity.Book;
import org.sang.service.Bookservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Bookservice bookservice;


    static String url = "http://HELLO-SERVICE/";


    @HystrixCommand(fallbackMethod = "errorMethod") //短路时调用的方法
    @GetMapping(value = "/ribbon-consumer")
    public String helloController() {
        String str = restTemplate.getForEntity("Http://HELLO-SERVICE/hello", String.class).getBody();
        System.out.println(str);

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class);

        HttpStatus statusCode = forEntity.getStatusCode();
        int statusCodeValue = forEntity.getStatusCodeValue();
        HttpHeaders headers = forEntity.getHeaders();

        System.out.println("statusCode:" + statusCode + "statusCodeValue:" + statusCodeValue + "headers:" + headers);

        return str;
    }

    public String errorMethod() {
        System.out.println("短路调用errorMethod");
        return "errorMethod";
    }

    /**
     * 传递参数方式 1
     * url + "providerSayHello?name={1}
     *
     * @return
     */
    @GetMapping(value = "/sayHello")
    public String sayHello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url + "providerSayHello?name={1}", String.class, "张三");

        String str = responseEntity.getBody();
        System.out.println(responseEntity.getBody());
        return str;
    }

    /**
     * 传递参数方式 2
     * url + "providerSayHello?name={name}
     *
     * @return
     */
    @GetMapping(value = "/sayHello2")
    public String sayHello2() {

        Map<String, String> map = new HashMap<>();
        map.put("name", "李四");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url + "providerSayHello?name={name}", String.class, map);
        System.out.println(responseEntity.getBody());
        return "Say hello2";
    }

    /**
     * 返回一个实体对象
     */

    @GetMapping(value = "/getBook")
    public Book getBook() {

        ResponseEntity<Book> forEntity = restTemplate.getForEntity(url + "/providerBook", Book.class);
        Book book = forEntity.getBody();
        return book;
    }

    @RequestMapping(value = "/providerBook2")
    public Book providerBook2() {
        Book book = new Book();
        book.setName("张三丰");
        book.setPrice(100L);
        ResponseEntity<Book> bookResponseEntity = restTemplate.postForEntity(url + "providerBook2", book, Book.class);
        Book body = bookResponseEntity.getBody();
        return body;
    }

    @RequestMapping("/test1")
    public Book test1() throws ExecutionException, InterruptedException {
        BookCommand bookCommand = new BookCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")), restTemplate);
        //Book book = bookCommand.execute();//同步调用
        Future<Book> queue = bookCommand.queue();//异步调用
        Book book = queue.get();  //V get(long timeout, TimeUnit unit) 可传入超时时间

        return book;
    }

    /**
     * 通过注解实现异步调用
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("/test2")
    public Book test2() throws ExecutionException, InterruptedException {

        /**
         * 此处异步调用, 可以在此段前后进行数据库操作(或者其他操作),也就是说数据库操作和本段程序一起执行,并不一定需要数据库操作完毕之后再返回数据,
         * 可以先返回结果,但数据库的操作还未结束.
         */
        Book book =   bookservice.test2().get();


        return book;
    }


}
