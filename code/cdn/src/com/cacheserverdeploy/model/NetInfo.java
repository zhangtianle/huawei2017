package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 4/1/17.
 */
public class NetInfo {

    private List<Evaluate> baga;
    private int edgeCount;   //边的个数
    private int NN;
    private int N;  //节点加上超级原点，汇点。
    private int S;  //超级原点
    private int T;  //超级汇点
    private int[] bandwidth;
    private int cost_server;
    private Edge[] edges;
    private int en = 0;
    private int sumFlow = 0;
    private int[] head;

    private List<Integer> T_list;
    private List<Integer> T_cost;

    private void add(int x, int y, int f, int c) {
        if (edges[en] == null) edges[en] = new Edge();
        edges[en].setFrom(x);
        edges[en].setTo(y);
        edges[en].setCap(f);
        edges[en].setCost(c);
        edges[en].setNext(head[x]);
        head[x] = en++;
    }

    public void addedge(int x, int y, int f, int c) {
        add(x, y, f, c);
        add(y, x, 0, -c);
    }

    public List<Evaluate> getBaga() {
        return baga;
    }

    public void setBaga(List<Evaluate> baga) {
        this.baga = baga;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }

    public int getNN() {
        return NN;
    }

    public void setNN(int NN) {
        this.NN = NN;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public int[] getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int[] bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getCost_server() {
        return cost_server;
    }

    public void setCost_server(int cost_server) {
        this.cost_server = cost_server;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }

    public int getEn() {
        return en;
    }

    public void setEn(int en) {
        this.en = en;
    }

    public int getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(int sumFlow) {
        this.sumFlow = sumFlow;
    }

    public int[] getHead() {
        return head;
    }

    public void setHead(int[] head) {
        this.head = head;
    }

    public List<Integer> getT_list() {
        return T_list;
    }

    public void setT_list(List<Integer> t_list) {
        T_list = t_list;
    }

    public List<Integer> getT_cost() {
        return T_cost;
    }

    public void setT_cost(List<Integer> t_cost) {
        T_cost = t_cost;
    }
}
