package com.cacheserverdeploy.model;

import java.util.ArrayList;

/**
 * Created by kyle on 4/1/17.
 */
public class Evaluate {
    private float error;
    private int cost;
    private ArrayList<String> list;

    public float getError() {
        return error;
    }

    public void setError(float error) {
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
