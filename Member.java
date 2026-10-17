package com.example;

// @author Aidan Garcia 
public class Member {
    private String name;
    private String interest;
    private int year;

    public Member(String name, String interest, int year) {
        this.name = name;
        this.interest = interest;
        this.year = year;
    }

    public String getName() { return name; }
    public String getInterest() { return interest; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return name + " | Interest: " + interest + " | Year: " + year;
    }
}
