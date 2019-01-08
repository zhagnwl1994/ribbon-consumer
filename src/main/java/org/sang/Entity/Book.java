package org.sang.Entity;

public class Book {
    private String name;
    private Long price;
    private Long num;

    public Book() {
    }

    public Book(String name, Long price, Long num) {
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}
