package com.cacheserverdeploy.deploy;

/**
 * Created by kyle on 4/5/17.
 */

/**
 * Created by kyle on 4/1/17.
 */
public class Edge {
    private int from;
    private int to;
    private int cap;
    private int cost;
    private int next;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }
}
