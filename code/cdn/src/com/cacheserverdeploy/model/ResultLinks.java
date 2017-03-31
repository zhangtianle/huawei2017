package com.cacheserverdeploy.model;

import java.util.List;

/**
 * Created by kyle on 3/30/17.
 */
public class ResultLinks {
    private List<Edge> edges;
    private int conId;
    private int bandwidth;

    public ResultLinks(List<Edge> edges, int conId, int bandwidth) {
        this.edges = edges;
        this.conId = conId;
        this.bandwidth = bandwidth;
    }

    public String getLinks() {
        String result = "";
        for (Edge i : edges) {
            result = result + i.getStartId() + " ";
        }
        return result + conId + " " + bandwidth;
    }
}
