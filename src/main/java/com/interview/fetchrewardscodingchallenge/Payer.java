package com.interview.fetchrewardscodingchallenge;

import java.io.Serializable;
import java.util.Date;

/*
Payer class for each Payer which adds or loses funds Implements Serializable in order to take in
JSON objects and Comparable in order to sort ArrayList of Payers by DateTime
*/
public class Payer implements Serializable, Comparable<Payer> {

    private String payerName;
    private Integer points;
    private Date timestamp;

    public Payer(String payerName, Integer points, Date timestamp) {
        this.payerName = payerName;
        this.points = points;
        this.timestamp = timestamp;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Payer comparePayer) {//Implements Comparator class to sort PayerList by DateTime format
        return timestamp.compareTo(comparePayer.getTimestamp());
    }
}
