package com.cacheserverdeploy.model;

/**
 * Created by kyle on 3/30/17.
 */
public class Edge {
    private int startId;
    private int endId;
    private int bandwidth;
    private int perFee;

    public Edge(int startId, int endId, int bandwidth, int perFee) {
        this.startId = startId;
        this.endId = endId;
        this.bandwidth = bandwidth;
        this.perFee = perFee;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    public int getEndId() {
        return endId;
    }

    public void setEndId(int endId) {
        this.endId = endId;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getPerFee() {
        return perFee;
    }

    public void setPerFee(int perFee) {
        this.perFee = perFee;
    }
}
