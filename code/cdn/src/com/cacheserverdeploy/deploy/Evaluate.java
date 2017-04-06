package com.cacheserverdeploy.deploy;

import java.util.ArrayList;

/**
 * Created by kyle on 4/1/17.
 */
public class Evaluate {
    private double error;
    private int cost;
    private ArrayList<String> list;

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
