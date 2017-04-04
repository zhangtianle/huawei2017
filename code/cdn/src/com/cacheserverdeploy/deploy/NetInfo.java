package com.cacheserverdeploy.deploy;

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
    /**
     * 单位服务器价格
     */
    private int cost_server;
    private List<Edge> edges;
    public static int en = 0;
    private int[] head;

    private List<Integer> T_list;
    private List<Integer> T_cost;

    private void add(int x, int y, int f, int c) {
        Edge e = new Edge();
        e.setFrom(x);
        e.setTo(y);
        e.setCap(f);
        e.setCost(c);
        e.setNext(head[x]);
        head[x] = en++;

        edges.add(e);
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

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
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
}
