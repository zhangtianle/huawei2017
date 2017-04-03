package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 4/1/17.
 */
public class NetInfo {

    private List<Evaluate> baga;
    private int edgeNum;   //边的个数
    private int nodeNum; //节点个数
    private int superNodeNum;  //节点加上超级原点，汇点。
    private int superS;  //超级原点
    private int superT;  //超级汇点
    private int[] bandwidth;
    private int serverNum;
    private int cost_server;
    private Edge[] edges;
    private int en = 0;
    private int[] head;

    private List<Integer> T_list;
    private List<Integer> T_cost;

    private void add(int x, int y, int f, int c) {
        if (edges[en] == null) {
            edges[en] = new Edge();
        }
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

    public int getEdgeNum() {
        return edgeNum;
    }

    public void setEdgeNum(int edgeNum) {
        this.edgeNum = edgeNum;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public int getSuperNodeNum() {
        return superNodeNum;
    }

    public void setSuperNodeNum(int superNodeNum) {
        this.superNodeNum = superNodeNum;
    }

    public int getSuperS() {
        return superS;
    }

    public void setSuperS(int superS) {
        this.superS = superS;
    }

    public int getSuperT() {
        return superT;
    }

    public void setSuperT(int superT) {
        this.superT = superT;
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

    public int getServerNum() {
        return serverNum;
    }

    public void setServerNum(int serverNum) {
        this.serverNum = serverNum;
    }
}
