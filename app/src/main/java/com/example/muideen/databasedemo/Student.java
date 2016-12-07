package com.example.muideen.databasedemo;

/**
 * Created by Adewale on 12/6/2016.
 */
public class Student {
    private String matricNo;
    private String name;
    private int age;

    public Student(String matricNo, String name, int age) {
        this.matricNo = matricNo;
        this.name = name;
        this.age = age;
    }

    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
