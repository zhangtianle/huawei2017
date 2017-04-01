package com.cacheserverdeploy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class Network {
    private int nodeNum;
    private int edgeNum;
    private int conNum;

    private int serverCost;

    private List<Edge> edgeList;
    private List<ConNode> conNodeList;

    public Network(int nodeNum, int edgeNum, int conNum, int serverCost, List<Edge> edgeList, List<ConNode> conNodeList) {
        this.nodeNum = nodeNum;
        this.edgeNum = edgeNum;
        this.conNum = conNum;
        this.serverCost = serverCost;
        this.edgeList = fullLink(edgeList);
        this.conNodeList = conNodeList;
    }

    private List<Edge> fullLink(List<Edge> list) {
        List<Edge> r = new ArrayList<Edge>();
        for (Edge e : list) {
            r.add(e);
            Edge edge = new Edge(e.getEndId(), e.getStartId(), e.getBandwidth(), e.getPerFee());
            r.add(edge);
        }
        return r;
    }

    public List<Edge> getNeighborEdge(int nodeId) {
        List<Edge> list = new ArrayList<Edge>();
        for (Edge e : edgeList) {
            int startId = e.getStartId();
            if (startId == nodeId) {
                list.add(e);
            }
        }
        return list;
    }

    public List<Integer> getNeighborNodeId(int nodeId) {
        List<Edge> neighborEdge = getNeighborEdge(nodeId);
        List<Integer> is = new ArrayList<Integer>();
        for (Edge e : neighborEdge) {
            int endId = e.getEndId();
            is.add(endId);
        }
        return is;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public void setEdgeNum(int edgeNum) {
        this.edgeNum = edgeNum;
    }

    public int getConNum() {
        return conNum;
    }

    public void setConNum(int conNum) {
        this.conNum = conNum;
    }

    public int getServerCost() {
        return serverCost;
    }

    public void setServerCost(int serverCost) {
        this.serverCost = serverCost;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public List<ConNode> getConNodeList() {
        return conNodeList;
    }

    public void setConNodeList(List<ConNode> conNodeList) {
        this.conNodeList = conNodeList;
    }
}
