package com.example.a68.intentapplication.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 68 on 08/07/2017.
 */

public class Person implements Serializable {

    private String name;
    private String web;
    private String phone;

    public Person() {
    }

    public static ArrayList<Person> getDataSet(){
        ArrayList<Person> arrayList = new ArrayList<>();
        arrayList.add(new Person("Google", "www.google.com", "1234"));
        arrayList.add(new Person("Hotmail", "www.hotmail.com", "456789"));
        arrayList.add(new Person("Facebook", "www.facebook.com", "123"));
        arrayList.add(new Person("Twitter", "www.twitter.com", "911"));
        return arrayList;
    }

    public Person(String name, String web, String phone) {
        this.name = name;
        this.web = web;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
