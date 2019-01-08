package org.sang.selfHystrixCommand;

import com.netflix.hystrix.HystrixCommand;
import org.sang.Entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BookCommand extends HystrixCommand<Book> {
    static String url = "http://HELLO-SERVICE/";
    private  RestTemplate restTemplate;

    public BookCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }
    /**
     * 此方法在服务故障时调用
     * @return
     */
    @Override
    protected Book getFallback() {
        System.out.println("=====測试自定义短路器======");
        return new Book("測试自定义短路器",404L,404L);
    }

    /**
     * 此方法是服务正常调用
     * @return
     * @throws Exception
     */
    @Override
    protected Book run() throws Exception {
        ResponseEntity<Book> forEntity = restTemplate.getForEntity(url + "/providerBook", Book.class);
        Book book = forEntity.getBody();
        System.out.println("服务正常调用");
        return book;
    }
}

